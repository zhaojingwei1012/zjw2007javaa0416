var vm = new Vue({
    el: '#deptdiv',
    data: {
        deptlist: [],
        pageNum: 1,
        pageSize: 3,
        page: {},
        searchEntity: {},
        postEntity: {},
        plist: [],
        postids: []
    },
    methods: {
        getDeptListConn: function () {
            var _this = this;
            axios.post("../dept/getDeptListConn.do?pageNum=" + _this.pageNum + "&pageSize=" + _this.pageSize, _this.searchEntity).then(function (response) {
                _this.deptlist = response.data.list;
                _this.pageNum = response.data.currentPage;
                _this.pageSize = response.data.pageSize;
                _this.page = response.data;

            });
        },
        paging: function (pageNum) {
            this.pageNum = pageNum;
            this.getDeptListConn();
        },
        toDeptPost: function (id) {
            var _this = this;
            axios.get("../dept/getDeptByDeptid.do?id=" + id).then(function (response) {
                _this.postEntity = response.data;
                _this.plist = response.data.plist;
                _this.postids = response.data.postids;
                document.getElementById("deptpostdiv").style.display = "block";
            });
        },
        saveDeptPost: function () {
            var _this = this;
            axios.post("../dept/saveDeptPost.do?deptid=" + _this.postEntity.id, _this.postids).then(function (response) {
                if (response.data.flag){
                    alert(response.data.msg);
                    document.getElementById("deptpostdiv").style.display = "none";
                    _this.getDeptListConn();
                }else{
                    alert(response.data.msg);
                }
            });
        }
    }
});
vm.getDeptListConn();