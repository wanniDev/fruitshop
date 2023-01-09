package me.ethereum.fruit.service

import jakarta.annotation.PostConstruct
import me.ethereum.fruit.model.Contract
import me.ethereum.fruit.model.Transactionfee
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.web3j.crypto.CipherException
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import java.io.File
import java.io.IOException
import java.math.BigInteger
import java.security.InvalidAlgorithmParameterException
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException


@Service
class ContractService(private val web3j: Web3j) {
    private val log: Logger = LoggerFactory.getLogger(ContractService::class.java)
    private val PASSWORD = "123456"
    private val GAS_PRICE = BigInteger.valueOf(1L)
    private val GAS_LIMIT = BigInteger.valueOf(500_000L)

    lateinit var credentials: Credentials
    private val contracts = ArrayList<String>()

    @PostConstruct
    @Throws(IOException::class, CipherException::class, NoSuchAlgorithmException::class,
        NoSuchProviderException::class, InvalidAlgorithmParameterException::class)
    fun init() {
        val dist = File("credentials")
        val file = WalletUtils.generateLightNewWalletFile(PASSWORD, dist)
        this.credentials = WalletUtils.loadCredentials(PASSWORD, "${dist.absolutePath}/$file")
        log.info("Credential crated: file={}, address={}", file, credentials.address)

        val coinbase = web3j.ethCoinbase().send()
        log.info("Coinbase: id: {}, address: {}, result: {}", coinbase.id, coinbase.address, coinbase.result)
        val transactionCnt = web3j.ethGetTransactionCount(coinbase.address, DefaultBlockParameterName.LATEST).send()
        val transaction = Transaction.createEtherTransaction(
            coinbase.address,                                   // from
            transactionCnt.transactionCount,                    // nonce
            BigInteger.valueOf(20_000_000_000L),            // gasPrice
            BigInteger.valueOf(21_000),                     // gasLimit
            credentials.address,                                // to
            BigInteger.valueOf(25_000_000_000_000_000L)     // value
        )
        web3j.ethSendTransaction(transaction).send()
        web3j.ethGetBalance(credentials.address, DefaultBlockParameterName.LATEST).send()
        val balance = web3j.ethGetBalance(credentials.address, DefaultBlockParameterName.LATEST).send()
        log.info("Balance: {}", balance.balance.toLong())
    }

    fun getOwnerAccount(): String {
        return credentials.address
    }

    @Throws(Exception::class)
    fun createContract(newContract: Contract?): Contract {
        val dist = File("credentials")
        val file = WalletUtils.generateLightNewWalletFile(PASSWORD, dist)
        val receiverCredentials = WalletUtils.loadCredentials(PASSWORD, "${dist.absolutePath}/$file")
        log.info("Credentials created: file={}, address={}", file, credentials.address)

        if (newContract != null) {
            val contract = Transactionfee.deploy(
                web3j,
                credentials,
                GAS_PRICE,
                GAS_LIMIT,
                receiverCredentials.address,
                newContract.fee?.let { BigInteger.valueOf(it) }
            ).send()

            newContract.receiver = receiverCredentials.address
            newContract.address = contract.contractAddress
            contracts.add(contract.contractAddress)
            log.info("New contract deployed: address={}", contract.contractAddress)
            val tr = contract.transactionReceipt
            if (tr.isPresent)
                log.info(
                    "Transaction receipt: from={}, to={}, gas={}",
                    tr.get().from,
                    tr.get().to,
                    tr.get().gasUsed.toInt())
        }
        return newContract ?: throw NullPointerException("null")
    }
}