pragma solidity ^0.4.0;
import "./user.sol";
import "./order.sol";
contract Tenant is User{
    string public gender;
    string public phoneNum;
    string public preference;
    string public education;
    string public vocation;
    string public economic;

    mapping(uint => address) public orderMapping;
    address[] public orderList;

    function Tenant(uint _id, string _name, string _password, string _gender, string _phoneNum,
        string _preference, string _education, string _vocation, string _economic)
    User ( _id,  _name,  _password) public {
        id = _id;
        name = _name;
        password = _password;
        gender = _gender;
        phoneNum = _phoneNum;
        preference = _preference;
        education = _education;
        vocation = _vocation;
        economic = _economic;
    }
}