package me.ethereum.fruit.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

@Configuration
class ContractConfig {
    @Bean
    fun web3j(): Web3j {
        return Web3j.build(HttpService("http://localhost:8588"))
    }
}