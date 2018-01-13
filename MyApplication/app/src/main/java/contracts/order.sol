pragma solidity ^0.4.0;
contract Order {

    uint public orderId;
    uint public tenantId;
    uint public landlordId;
    uint public price;
    uint public createDate;
    string public facilities;
    string public roomType;
    string public hotelType;
    string public aroundSite;
    uint public startTime;
    uint public leaveTime;
    uint public roomNum;
    // uint public point;
    string public roomAddress;

    function Order(uint _orderId, uint _tenantId, uint _landlordId, uint _price, uint _createDate
    , string _facilities, string _roomType,string _hotelType, string _aroundSite, uint _startTime,
        uint _leaveTime, uint _roomNum, string _roomAddress) public {
        orderId = _orderId;
        tenantId = _tenantId;
        landlordId = _landlordId;
        price = _price;
        createDate = _createDate;
        facilities = _facilities;
        roomType = _roomType;
        hotelType = _hotelType;
        aroundSite = _aroundSite;
        startTime = _startTime;
        leaveTime = _leaveTime;
        roomNum = _roomNum;
        //   point = _point;
        roomAddress = _roomAddress;
    }

}