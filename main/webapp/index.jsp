<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Test-Page</title>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <script src="http://code.jquery.com/jquery-latest.js"></script>

    <script src="/js/jquery-3.3.1.min.js"></script>

    <script>
        function getAllFundInfo() {
            $.post('/BigVFund_war_exploded/fund/info', {}, function (data) {
                console.log(data);
                for (let i = 0; i < data.length; i++) {
                    console.log(data[i].fcode + " " + data[i].big_V_name + " " +
                        data[i].fund_name + " " + data[i].fans_num + " " +
                        data[i].evaluation + " " + data[i].ftypeName + " " +
                        data[i].zhbintervalRate + " " + data[i].accountExistTime + " " +
                        data[i].assetVol + " ");
                }
            }, "json");
        }
    </script>
    <script>
        function getFundNav() {
            $.post('/BigVFund_war_exploded/fund/nav', {
                FCode: "10073527",
                BeginTime: "1553097600000",
                EndTime: "1555603200000"
            }, function (data) {
                console.log(data);
                let parse = JSON.parse(data);
                console.log(parse.length);
                for (let i = 0; i < parse.length; i++) {
                    console.log(parse[i]);
                }
            })
        }
    </script>
    <script>
        function getFundDate() {
            let funds = [];
            funds.push("10073527");
            funds.push("10018301");
            funds.push("10005141");
            /*            $.post('/BigVFund_war_exploded/fund/date', {
                            "funds": ["10073527","10018301"]
                        }, function (data) {
                            console.log(data);
                        })*/
            $.ajax({
                type: "POST",
                url: "/BigVFund_war_exploded/fund/date",
                data: {"funds": ["10073527", "10018301", "10005141"]},
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    console.log(data);
                }
            })
        }
    </script>
    <script>
        function getFundData() {
            $.post('/BigVFund_war_exploded/fund/data', {
                FCode: "10073527",
                BeginTime: "1553097600000",
                EndTime: "1629216000000"
            }, function (data) {
                console.log(data);
            })
        }
    </script>
    <script>
        function userRegister() {
            $.post('/BigVFund_war_exploded/user/register', {
                username: "zcm",
                password: "zcm",
                nickname: "zhou"
            }, function (data) {
                console.log(data);
                if (parseInt(data["register"]) === 1) {
                    console.log("register successfully");
                } else {
                    console.log("register fail");
                }
            }, "json");
        }
    </script>
    <script>
        function userLogin() {
            $.post('/BigVFund_war_exploded/user/login', {
                username: "zcm",
                password: "zcm",
            }, function (data) {
                console.log(data);
                if (parseInt(data["login"]) === 1) {
                    console.log("login successfully");
                } else {
                    console.log("login fail");
                }
            }, "json");
        }
    </script>
    <script>
        function userCollect() {
            $.post('/BigVFund_war_exploded/user/collect', {
                username: "zcm",
                FCode: "10757296",
            }, function (data) {
                console.log(data);
                if (parseInt(data["collect"]) === 1) {
                    console.log("collect successfully");
                } else {
                    console.log("collect fail");
                }
            }, "json");
        }
    </script>
    <script>
        function userCheck() {
            $.post('/BigVFund_war_exploded/user/check', {
                username: "zcm",
            }, function (data) {
                console.log(data);
            }, "json");
        }
    </script>
    <script>
        function userCustomCombine() {

            const username = "zcm";
            let collections = [];
            collections.push({FCode: "10074390", ratio: 0.3});
            collections.push({FCode: "11860969", ratio: 0.5});
            collections.push({FCode: "10015297", ratio: 0.2});
            const datas = {username: username, collectionInfo: collections};

            $.ajax({
                type: "POST",
                url: "/BigVFund_war_exploded/user/customCombine",
                data: JSON.stringify(datas),
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    console.log(data);
                }
            })
        }
    </script>
    <script>
        function userCustomCombineCheck() {
            $.post("/BigVFund_war_exploded/user/customCheck", {
                username: "zcm",
            }, function (data) {
                console.log(data);
            }, "json");
        }
    </script>
</head>
<body>
<br/>
<input type="button" value="getAllFundInfo" onclick="getAllFundInfo()"/>
<input type="button" value="getFundNav" onclick="getFundNav()"/>
<input type="button" value="getFundDate" onclick="getFundDate()"/>
<input type="button" value="getFundData" onclick="getFundData()"/>
<br/>
<a>user-test</a>
<br/>
<input type="button" value="userRegister" onclick="userRegister()"/>
<input type="button" value="userLogin" onclick="userLogin()"/>
<input type="button" value="userCollect" onclick="userCollect()"/>
<input type="button" value="userCheck" onclick="userCheck()"/>
<input type="button" value="userCustomCombine" onclick="userCustomCombine()"/>
<input type="button" value="userCustomCombineCheck" onclick="userCustomCombineCheck()"/>
</body>
</html>