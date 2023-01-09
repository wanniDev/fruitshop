package me.ethereum.fruit.controller

import me.ethereum.fruit.model.Contract
import me.ethereum.fruit.service.ContractService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class SmartContractApi(private val contractService: ContractService) {

    @GetMapping("/contract/owner")
    fun getOwnerAccount(): String {
        return contractService.getOwnerAccount()
    }

    @PostMapping("/contract")
    @Throws(Exception::class)
    fun createContract(@RequestBody newContract: Contract?): Contract {
        return contractService.createContract(newContract)
    }
}