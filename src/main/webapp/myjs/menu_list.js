var vm = new Vue({
    el: '#menudiv',
    data: {
        menulist: [],
        pid: 1,
        entity: {}
    },
    methods: {
        getMenuListByPid: function (pid) {
            this.pid = pid;//把pid赋值上去
            var _this = this;
            axios.get("../menu/getMenuListByPid.do?pid=" + pid).then(function (response) {
                _this.menulist = response.data;
            });
        },
        toAddMenu: function () {
            this.entity = {};
            document.getElementById("menuupdatediv").style.display = "block";
        },
        saveMenu: function () {
            this.entity.pid = this.pid;
            var _this = this;
            axios.post("../menu/saveMenu.do", _this.entity).then(function (response) {
                    if (response.data.flag){
                        _this.getMenuListByPid(_this.pid);
                        document.getElementById("menuupdatediv").style.display = "none";
                    }else{
                        alert(response.data.msg);
                    }
            });
        },
        deleteMenuById: function (id) {
            var _this = this;
            axios.get("../menu/deleteMenuById.do?id=" + id).then(function (response) {
                if(response.data.flag){
                    _this.getMenuListByPid(_this.pid);
                }else{
                    alert(response.data.msg);
                }
            });
        }
    }
});
vm.getMenuListByPid(1);