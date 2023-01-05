// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;

contract Answer {
  uint answer;

  function setAnswer(uint _answer) public {
    answer = _answer;
  }

  function getAnswer() view public returns (uint) {
    return answer;
  }
}
