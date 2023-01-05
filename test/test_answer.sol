pragma solidity >=0.4.22 <0.9.0;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "../contracts/Answer.sol";
contract TestAnswer {
    function testAnswer() public {
        Answer a = new Answer();
        uint _expected = 42;
        a.setAnswer(_expected);
        Assert.equal(a.getAnswer(), _expected, "The Answer is 42.");
    }
}