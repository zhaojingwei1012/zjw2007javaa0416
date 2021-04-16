var vm = new Vue({
    el: '#userdiv',
    data: {
        userBean: [],
        entity: {},
        dlist: [{postids: []}],//在dlist定义一个空对象，将postids:[]声明一下
        deptids: []
    },
    methods: {
        getUserList: function () {
            var _this = this;
            axios.get("../user/getUserList.do").then(function (response) {
                _this.userBean = response.data;
            });
        },
        toUserDept: function (id) {
            /**
             * 查出这个用户已经拥有的科室id
             * 全部的科室
             * 查询一个对象，完全可以
             * 也可以把user丰富一下，直接查询一个user
             */
            var _this = this;
            axios.get("/user/getUserVoById.do?id=" + id).then(function (response) {
                document.getElementById("userdeptdiv").style.display = "block";
                _this.entity = response.data;
                _this.dlist = response.data.dlist;
                _this.deptids = response.data.deptids;
            });
        },
        saveUserDept: function () {
            //把用户id和deptids传到后台，在service中，先删除再添加
            var _this = this;
            axios.post("../user/saveUserDept.do?id=" + _this.entity.id, _this.deptids).then(function (response) {
                if (response.data.flag){
                    alert(response.data.msg);
                    document.getElementById("userdeptdiv").style.display = "none";
                    _this.getUserList();
                }else{
                    alert(response.data.msg);
                }
            });
        },
        toUserPost: function (id) {
            var _this = this;
                axios.get("../user/getUserInfo.do?id=" + id).then(function (response) {
                    _this.entity = response.data;
                    _this.dlist = response.data.dlist;
                    document.getElementById("userpostdiv").style.display = "block";
                });
        },
        saveUserPost: function () {
            this.entity.dlist = this.dlist;
            var _this = this;
            axios.post("../user/saveUserPost.do", _this.entity).then(function (response) {
                if (response.data.flag){
                    alert(response.data.msg);
                    document.getElementById("userpostdiv").style.display = "none";
                }else{
                    alert(response.data.msg);
                }
            });
        }
    }
});
vm.getUserList();