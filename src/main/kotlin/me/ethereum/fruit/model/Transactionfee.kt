package me.ethereum.fruit.model

import me.ethereum.fruit.model.Transactionfee
import org.web3j.abi.EventEncoder
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.*
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.RemoteCall
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.Log
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.Contract
import org.web3j.tx.TransactionManager
import rx.Observable
import java.math.BigInteger
import java.util.*

/**
 *
 * Auto generated code.
 *
 * **Do not modify!**
 *
 * Please use the [web3j command line tools](https://docs.web3j.io/command_line.html),
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * [codegen module](https://github.com/web3j/web3j/tree/master/codegen) to update.
 *
 *
 * Generated with web3j version 3.4.0.
 */
open class Transactionfee : Contract {
    protected constructor(
        contractAddress: String?,
        web3j: Web3j?,
        credentials: Credentials?,
        gasPrice: BigInteger?,
        gasLimit: BigInteger?
    ) : super(
        BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit
    ) {
    }

    protected constructor(
        contractAddress: String?,
        web3j: Web3j?,
        transactionManager: TransactionManager?,
        gasPrice: BigInteger?,
        gasLimit: BigInteger?
    ) : super(
        BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit
    ) {
    }

    val receiverBalance: RemoteCall<BigInteger>
        get() {
            val function = Function(
                FUNC_GETRECEIVERBALANCE,
                Arrays.asList(),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
            )
            return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
        }

    fun balances(param0: String?): RemoteCall<BigInteger> {
        val function = Function(
            FUNC_BALANCES,
            Arrays.asList<Type<*>>(Address(param0)),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    fun sendTrx(weiValue: BigInteger?): RemoteCall<TransactionReceipt> {
        val function = Function(
            FUNC_SENDTRX,
            Arrays.asList(), emptyList()
        )
        return executeRemoteCallTransaction(function, weiValue)
    }

    fun fee(): RemoteCall<BigInteger> {
        val function = Function(
            FUNC_FEE,
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    fun receiver(): RemoteCall<String> {
        val function = Function(
            FUNC_RECEIVER,
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Address?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, String::class.java)
    }

    fun getSentEvents(transactionReceipt: TransactionReceipt?): List<SentEventResponse> {
        val valueList = extractEventParametersWithLog(SENT_EVENT, transactionReceipt)
        val responses = ArrayList<SentEventResponse>(valueList.size)
        for (eventValues in valueList) {
            val typedResponse = SentEventResponse()
            typedResponse.log = eventValues.log
            typedResponse.from = eventValues.nonIndexedValues[0].value as String
            typedResponse.to = eventValues.nonIndexedValues[1].value as String
            typedResponse.amount = eventValues.nonIndexedValues[2].value as BigInteger
            typedResponse.sent = eventValues.nonIndexedValues[3].value as Boolean
            responses.add(typedResponse)
        }
        return responses
    }

    fun sentEventObservable(filter: EthFilter?): Observable<SentEventResponse> {
        return web3j.ethLogObservable(filter).map { log ->
            val eventValues = extractEventParametersWithLog(SENT_EVENT, log)
            val typedResponse = SentEventResponse()
            typedResponse.log = log
            typedResponse.from = eventValues.nonIndexedValues[0].value as String
            typedResponse.to = eventValues.nonIndexedValues[1].value as String
            typedResponse.amount = eventValues.nonIndexedValues[2].value as BigInteger
            typedResponse.sent = eventValues.nonIndexedValues[3].value as Boolean
            typedResponse
        }
    }

    fun sentEventObservable(
        startBlock: DefaultBlockParameter?,
        endBlock: DefaultBlockParameter?
    ): Observable<SentEventResponse> {
        val filter = EthFilter(startBlock, endBlock, getContractAddress())
        filter.addSingleTopic(EventEncoder.encode(SENT_EVENT))
        return sentEventObservable(filter)
    }

    class SentEventResponse {
        var log: Log? = null
        var from: String? = null
        var to: String? = null
        var amount: BigInteger? = null
        var sent: Boolean? = null
    }

    companion object {
        private const val BINARY =
            "608060405234801561001057600080fd5b506040516040806102c783398101604052805160209091015160018054600160a060020a03909316600160a060020a03199093169290921790915560005561026a8061005d6000396000f30060806040526004361061006c5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166310fb7bc6811461007157806327e235e314610098578063d0f452dd146100c6578063ddca3f43146100d0578063f7260d3e146100e5575b600080fd5b34801561007d57600080fd5b50610086610123565b60408051918252519081900360200190f35b3480156100a457600080fd5b5061008673ffffffffffffffffffffffffffffffffffffffff60043516610140565b6100ce610152565b005b3480156100dc57600080fd5b5061008661021c565b3480156100f157600080fd5b506100fa610222565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b60015473ffffffffffffffffffffffffffffffffffffffff163190565b60026020526000908152604090205481565b600080548190606490340260015460405192909104935073ffffffffffffffffffffffffffffffffffffffff169083156108fc029084906000818181858888f16001805473ffffffffffffffffffffffffffffffffffffffff90811660009081526002602090815260409182902080548c01905592548151338152921692820192909252808201899052821515606082015290519196507f4a7ca93981c43b6021cfef8fa7764ad1a6abd748f97622b8fc50d887bf603c6495508190036080019350915050a15050565b60005481565b60015473ffffffffffffffffffffffffffffffffffffffff16815600a165627a7a7230582056902cd076d831b0bb2b8ffad0441a608f0fc2f58ca92718e460b34e34f24cad0029"
        const val FUNC_GETRECEIVERBALANCE = "getReceiverBalance"
        const val FUNC_BALANCES = "balances"
        const val FUNC_SENDTRX = "sendTrx"
        const val FUNC_FEE = "fee"
        const val FUNC_RECEIVER = "receiver"
        val SENT_EVENT = Event(
            "Sent",
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(
                object : TypeReference<Address?>() {},
                object : TypeReference<Address?>() {},
                object : TypeReference<Uint256?>() {},
                object : TypeReference<Bool?>() {})
        )

        fun deploy(
            web3j: Web3j?,
            credentials: Credentials?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?,
            _receiver: String?,
            _fee: BigInteger?
        ): RemoteCall<Transactionfee> {
            val encodedConstructor = FunctionEncoder.encodeConstructor(
                Arrays.asList<Type<*>>(
                    Address(_receiver),
                    Uint256(_fee)
                )
            )
            return deployRemoteCall(
                Transactionfee::class.java,
                web3j,
                credentials,
                gasPrice,
                gasLimit,
                BINARY,
                encodedConstructor
            )
        }

        fun deploy(
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?,
            _receiver: String?,
            _fee: BigInteger?
        ): RemoteCall<Transactionfee> {
            val encodedConstructor = FunctionEncoder.encodeConstructor(
                Arrays.asList<Type<*>>(
                    Address(_receiver),
                    Uint256(_fee)
                )
            )
            return deployRemoteCall(
                Transactionfee::class.java,
                web3j,
                transactionManager,
                gasPrice,
                gasLimit,
                BINARY,
                encodedConstructor
            )
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): Transactionfee {
            return Transactionfee(contractAddress, web3j, credentials, gasPrice, gasLimit)
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): Transactionfee {
            return Transactionfee(contractAddress, web3j, transactionManager, gasPrice, gasLimit)
        }
    }
}