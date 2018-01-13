pragma solidity ^0.4.0;
contract Tender {

    uint public tenderId;
    uint public orderId;
    uint public tenantId;
    uint public price;
    string public roomAddress;
    uint public startTime;
    uint public leaveTime;
    string public roomType;
    string public hotelType;
    uint8 public roomNum;
    uint public createdAt;
    string public facilities;
    string public aroundSite;

    function Bid(uint _tenderId, uint _orderId, uint _tenantId, uint _price, string _roomAddress,
        uint _startTime, uint _leaveTime, string _roomType, string _hotelType, uint8 _roomNum,
        uint _createdAt, string _facilities, string _aroundSite) public {
        tenderId = _tenderId;
        orderId = _orderId;
        tenantId = _tenantId;
        price = _price;
        roomAddress = _roomAddress;
        startTime = _startTime;
        leaveTime = _leaveTime;
        roomType = _roomType;
        hotelType = _hotelType;
        roomNum = _roomNum;
        createdAt = _createdAt;
        facilities = _facilities;
        aroundSite = _aroundSite;
    }

}