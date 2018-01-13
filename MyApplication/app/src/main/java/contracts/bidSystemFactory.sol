pragma solidity ^0.4.0;
import "./landlord.sol";
import "./tenant.sol";
// import "./room.sol";
import "./bid.sol";
import "./tender.sol";
contract BidSystemFactory {
    mapping(uint => address) public landlordMapping;
    address[] public landlordList;

    mapping(uint => address) public tenantMapping;
    address[] public tenantList;

    mapping(uint => address) public roomMapping;
    address[] public roomList;

    //pending...
    // mapping(uint => address) public orderMapping;
    // address[] public orderList;

    mapping(uint => address) public bidMapping;
    address[] public bidList;

    mapping(uint => address) public tenderMapping;
    address[] public tenderList;


    /*
    Write creation functions below.
    */
    function createLandlord (uint _id, string _name, string _password, bytes32[] _otherInfo) {
        address newLandlord = new Landlord(_id, _name, _password, _otherInfo);
        landlordList.push(newLandlord);
        landlordMapping[_id] = landlordList[landlordList.length - 1];
    }
}