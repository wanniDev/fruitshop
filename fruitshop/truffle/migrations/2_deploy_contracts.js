var Shop = artifacts.require("./Shop.sol");

module.exports = function(_deployer) {
  // Use deployer to state migration tasks.
    _deployer.deploy(Shop);
};
