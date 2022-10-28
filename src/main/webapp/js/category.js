layui.use(['element', 'carousel', 'layer', 'laypage', 'util', 'form'], function () {
    let element = layui.element;
    let carousel = layui.carousel;
    let layer = layui.layer;
    let laypage = layui.laypage;
    let util = layui.util
    let form = layui.form

    const {
        createEditor,
        createToolbar
    } = window.wangEditor

    //判断是否登录
    let loginFlag = $.cookie("loginFlag")
    if (loginFlag == "" || loginFlag == null) {
        loginFlag = 0
    }
    console.log(loginFlag)
    if (parseInt(loginFlag) != 1) {
        alert("请先登录！")
        window.location.href = `./index.html`
    }

    //取浏览器地址的参数
    let cId = window.location.search.split("=")[1]
    console.log(cId)


    util.fixbar({
        showHeight: 100,
        click: function () {
            // console.log('点击了')
        }
    })

    let categoryList = null
    /** 获取加载分类 */
    let loadCategoryList = function () {
        //获取categoryList
        $.ajax({
            url: "../category",
            type: "get",
            data: {
                method: "getCategoryList"
            },
            async: false,
            success(res) {
                categoryList = res
                // console.log(27, res)
                //渲染到页面上
                let str = ``
                // let str = `<div id="categoryList">`
                let str2 = ``
                for (let i = 0; i < res.length; i++) {
                    str += `<a href="" target="_blank" class="categoryDetail" c_id="${res[i].id}">${res[i].name}</a>`
                    str2 += `<div class="slideshowDetail" c_id="${res[i].id}" style="cursor: pointer;text-align: center;background-image: url('${res[i].img}');background-repeat: no-repeat;background-size: 100% 100%;color: #ff6200;font-size: 18px ">${res[i].name}</div>`
                }

                $("#categorySon").append(str)
                $("#slideshowImg").append(str2)
                //导航重置
                element.render()
            }
        })
    }
    /** 根据cookie里的userId获取user */
    let getUser = function () {
        let user = null
        let userId = $.cookie("userId");
        if (userId == "" || userId == null) {
            userId = 0
        } else {
            userId = parseInt(userId)
            // console.log(252, userId)
        }
        $.ajax({
            url: "../user",
            type: "post",
            data: {
                method: "getUserById",
                id: userId
            },
            success(res) {
                user = res
            },
            async: false
        })
        return user
    }

    /** 渲染用户信息 */
    let setUser = function (user) {
        // const user = getUser()
        if (user != null) {
            $("#userName").text(user.userName)
            $("#userHeadImg").attr("src", user.img)
            $("#userDo").html(`
                    <dd><a href="javascript:;" id="userChange">修改信息</a></dd>
                    <dd><a href="javascript:;" id="topicPublish">发布话题</a></dd>
                    <dd><a href="javascript:;" id="logOut">退出登录</a></dd>
                                `)
        } else {
            $("#userName").text("请登录")
            $("#userHeadImg").attr("src", "")
            $("#userDo").html(`
                    <dd><a href="javascript:;" id="login">登录</a></dd>
                <dd><a href="javascript:;" id="register">注册</a></dd>
                                `)
        }
    }

    let user = getUser()
    setUser(user)
    loadCategoryList()


    /** 分类a标签的跳转 */
    $(".categoryWrap").on("click", ".categoryDetail", function () {
        let newHref = `./category.html?c_id=${$(this).attr("c_id")}`
        $(this).attr("target", "_blank")
        $(this).attr("href", newHref)
    })


    /** 搜索框 */
    $("#searchBtn").on("click", function () {
        let userId = $.cookie("userId")
        let searchKey = $("#searchInput").val()
        if (userId == null) {
            layer.msg('请先登录', {
                    icon: 4,
                    time: 2000
                }, function () {
                    login()
                }
            )
        } else if (searchKey == "") {
            layer.msg('请先输入想要搜索的内容', {
                    icon: 3,
                    time: 2000
                }, function () {
                    $("#searchInput").select()
                }
            )
        } else {
            $.ajax({
                url: '../topic',
                type: "post",
                data: {
                    method: "searchByKey",
                    searchKey
                },
                success(res) {
                    // console.log(res)
                    if (res.length == 0) {
                        alert("未查询到")
                    } else {
                        $("#searchInput").attr("placeholder", searchKey)
                        window.open(`./search.html?key=${searchKey}`)
                    }
                },
                async: false
            })
        }
    })

    /** 用户界面的跳转 */
    $("#user").on("click", "#userHref", function (event) {
        let userId = $.cookie("userId")
        if (userId == null) {
            layer.msg('请先登录', {
                    icon: 4,
                    time: 2000
                }, function () {
                    login()
                }
            )
            event.preventDefault()
        } else {
            $(this).attr("target", "_blank")
            $(this).attr("href", `./user.html?userId=${userId}`)
        }

    })

    /** 登录弹出层 */
    let login = function () {
        layer.open({
            type: 1,
            title: ["登录处理", "text-align: center;font-size:18px"],
            content: $("#loginBody"),
            skin: "layui-layer-molv",
            area: '500px',
            btn: ['清空', '登录'],
            yes: function (index, layero) {
                $("#phoneInput").val("")
                $("#pwdInput").val("")
            },
            btn2: function (index, layero) {
                if ($("#phoneInput").val() == "") {
                    layer.msg('手机号不能为空', {
                        icon: 5,
                        time: 1000
                    }, function () {
                        $("#phoneInput").select()
                    })
                } else if ($("#pwdInput").val() == "") {
                    layer.msg('密码不能为空', {
                        icon: 5,
                        time: 1000
                    }, function () {
                        $("#pwdInput").select()
                    })
                } else {
                    $.ajax({
                        url: "../user",
                        type: "post",
                        data: {
                            method: "login",
                            phone: $("#phoneInput").val(),
                            pwd: $("#pwdInput").val()
                        },
                        success(res) {
                            user = res
                            // console.log(194, res)
                            if (res != null) {
                                layer.msg('登录成功', {
                                    icon: 1,
                                    time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                }, function () {
                                    $("#loginBody").css("display", "none")
                                    layer.close(index)
                                });
                            } else {
                                layer.msg('登录失败', {
                                    icon: 2,
                                    time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                })
                            }
                            setUser(res)
                        },
                        async: false
                    })
                }

                // $("#registerBody").css("display", "none")
                return false
            },
            cancel: function (index, layero) {
                if (confirm('确定要关闭么')) { //只有当点击confirm框的确定时，该层才会关闭
                    layer.close(index)
                    $("#loginBody").css("display", "none")
                }
                return false;
            },
            anim: 3,
            shade: 0.3
        })
    }
    $("#user").on("click", "#login", function () {
        login()
    })


    /** 弹出注册层 */
    let register = function () {
        let sex = ""
        layer.open({
            type: 1,
            title: ["注册处理", "text-align: center;font-size:18px"],
            content: $("#registerBody"),
            skin: "layui-layer-molv",
            area: '500px',
            btn: ['清空', '注册'],
            yes: function (index, layero) {
                $("#registerPhone").val("")
                $("#registerPwd").val("")
            },
            btn2: function (index, layero) {
                if ($("#registerName").val() == "") {
                    layer.msg('用户名不能为空', {
                        icon: 5,
                        time: 1000
                    }, function () {
                        $("#registerName").select()
                    })
                } else if ($("#registerPhone").val() == "") {
                    layer.msg('手机号不能为空', {
                        icon: 5,
                        time: 1000
                    }, function () {
                        $("#registerPhone").select()
                    })
                } else if ($("#registerPwd").val() == "") {
                    layer.msg('密码不能为空', {
                        icon: 5,
                        time: 1000
                    }, function () {
                        $("#registerPwd").select()
                    })
                } else {
                    sex = $('.sexBox input[name="sex"]:checked ').val()
                    if ($(".male").attr("checked")) {
                        sex = "男"
                    } else {
                        sex = "女"
                    }
                    console.log(sex)
                    $.ajax({
                        url: "../user",
                        type: "post",
                        data: {
                            method: "register",
                            phone: $("#registerPhone").val(),
                            pwd: $("#registerPwd").val(),
                            name: $("#registerName").val(),
                            sex
                        },
                        success(res) {
                            console.log(283, res)
                            if (res == 1) {
                                layer.msg('注册成功', {
                                    icon: 1,
                                    time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                }, function () {
                                    $("#registerBody").css("display", "none")
                                    layer.close(index)
                                });
                            } else {
                                layer.msg('注册失败', {
                                    icon: 2,
                                    time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                })
                            }
                        },
                        async: false
                    })
                }

                return false
            },
            cancel: function (index, layero) {
                if (confirm('确定要关闭么')) { //只有当点击confirm框的确定时，该层才会关闭
                    $("#registerBody").css("display", "none")
                    layer.close(index)
                }
                return false;
            },
            anim: 3,
            shade: 0.3
        })
    }
    $("#user").on("click", "#register", function () {
        register()
    })

    /** 弹出修改信息层 */
    let userChange = function () {
        // console.log(user)
        let sex
        if (user.sex == 1) {
            sex = "男"
        } else {
            sex = "女"
        }
        $("#changeName").val(user.userName)
        $("#changePhone").val(user.phone)
        $("#changePwd").val(user.pwd)
        for (let i = 0; i < $("#changeSexBox input").length; i++) {
            if ($(`#changeSexBox input:eq(${i})`).val() == sex) {
                $(`#changeSexBox input:eq(${i})`).attr("checked", true)
            }
        }
        form.render()
        layer.open({
            type: 1,
            title: ["用户信息修改", "text-align: center;font-size:18px"],
            content: $("#changeBody"),
            skin: "layui-layer-molv",
            area: '500px',
            btn: ['清空', '提交'],
            yes: function (index, layero) {
                $("#changePhone").val("")
                $("#changePwd").val("")
            },
            btn2: function (index, layero) {
                for (let i = 0; i < $("#changeSexBox input").length; i++) {
                    if ($(`#changeSexBox input:eq(${i})`).is(":checked")) {
                        sex = $(`#changeSexBox input:eq(${i})`).val()
                    }
                }
                // console.log(sex)
                if ($("#changeName").val() == "") {
                    layer.msg('用户名不能为空', {
                        icon: 5,
                        time: 1000
                    }, function () {
                        $("#changeName").select()
                    })
                } else if ($("#changePhone").val() == "") {
                    layer.msg('手机号不能为空', {
                        icon: 5,
                        time: 1000
                    }, function () {
                        $("#changePhone").select()
                    })
                } else if ($("#changePwd").val() == "") {
                    layer.msg('密码不能为空', {
                        icon: 5,
                        time: 1000
                    }, function () {
                        $("#changePwd").select()
                    })
                } else {
                    $.ajax({
                        url: "../user",
                        type: "post",
                        data: {
                            method: "update",
                            id: $.cookie("userId"),
                            phone: $("#changePhone").val(),
                            pwd: $("#changePwd").val(),
                            name: $("#changeName").val(),
                            sex
                        },
                        success(res) {
                            if (res == 1) {
                                layer.msg('修改成功', {
                                    icon: 1,
                                    time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                }, function () {
                                    $("#changeBody").css("display", "none")
                                    layer.close(index)

                                });
                            } else {
                                layer.msg('修改失败', {
                                    icon: 2,
                                    time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                })
                                // $("#change").onclick()
                            }
                        },
                        async: false
                    })
                }
                return false
            },
            cancel: function (index, layero) {
                if (confirm('确定要关闭么')) { //只有当点击confirm框的确定时，该层才会关闭
                    $("#changeBody").css("display", "none")
                    layer.close(index)
                }
                return false;
            },
            anim: 3,
            shade: 0.3
        })
    }
    $("#user").on("click", "#userChange", function () {
        userChange()
    })
    /**创建富文本编辑器*/
    let createE = function () {

        const editorConfig = {}
        editorConfig.placeholder = '请输入内容'
        editorConfig.onChange = (editor) => {
            // 当编辑器选区、内容变化时，即触发
            // console.log('content', editor.children)
            // console.log('html', editor.getHtml())
        }

        // 创建编辑器
        const editor = createEditor({
            selector: '#editor-container',
            config: editorConfig,
            mode: 'default' // 或 'simple' 参考下文
        })
        // 创建工具栏
        const toolbar = createToolbar({
            editor,
            selector: '#toolbar-container',
            mode: 'default' // 或 'simple' 参考下文
        })

        return editor
    }

    /**  发布主题弹出层 */
    let topicPublish = function () {
        let editor
        for (let i = 0; i < categoryList.length; i++) {
            $("#categorySelect").append(`
            <option value=${i} cId=${categoryList[i].id}>${categoryList[i].name}</option>
            `)
        }
        form.render('select')
        layer.open({
            type: 1,
            title: ["用户发布主题", "text-align: center;font-size:18px"],
            content: $("#publishBody"),
            skin: "layui-layer-molv",
            area: ['800px'],
            btn: ['清空', '提交'],
            success: function () {
                /** 富文本编辑器*/
                editor = createE()
            },
            yes: function (index, layero) {
                $("#titleInput").val("")
                editor.clear()
            },
            btn2: function (index, layero) {
                if ($("#titleInput").val() == "") {
                    layer.msg('标题不能为空', {
                        icon: 5,
                        time: 1000
                    }, function () {
                        $("#titleInput").select()

                    })
                } else if (editor.getHtml() == "<p><br></p>") {
                    layer.msg('内容不能为空', {
                        icon: 5,
                        time: 1000
                    }, function () {
                        $("#editor-container").select()
                    })
                } else {
                    let cId
                    /** 获取被选择的option*/
                    for (let j = 0; j < $("#categorySelect option").length; j++) {
                        if ($(`#categorySelect option:eq(${j})`).is(":selected")) {
                            cId = $(`#categorySelect option:eq(${j})`).attr("cId")
                            // console.log(368, cId)
                        }
                    }
                    $.ajax({
                        url: "../topic",
                        type: "post",
                        data: {
                            method: "publishTopic",
                            cId,
                            userId: $.cookie("userId"),
                            title: $("#titleInput").val(),
                            content: editor.getHtml()
                        },
                        success(res) {
                            // console.log(382, res)
                            if (res == 1) {
                                layer.msg('发表成功', {
                                    icon: 1,
                                    time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                }, function () {
                                    $("#publishBody").css("display", "none")
                                    layer.close(index)
                                    editor.destroy()
                                    window.location.reload()
                                });
                            } else {
                                layer.msg('发表失败', {
                                    icon: 2,
                                    time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                })
                                // $("#").onclick()
                            }
                        },
                        async: false
                    })
                }
                return false
            },
            cancel: function (index, layero) {
                if (confirm('确定要关闭么')) { //只有当点击confirm框的确定时，该层才会关闭
                    $("#publishBody").css("display", "none")
                    layer.close(index)
                    editor.destroy()
                }
                return false;
            },
            anim: 3,
            shade: 0.3
        })
    }
    $("#userDo").on("click", "#topicPublish", function () {
        topicPublish()
    })

    /** 退出登录 */
    $("#userDo").on("click", "#logOut", function (event) {
        $.ajax({
            url: "../user",
            type: "post",
            data: {
                method: "logOut"
            },
            async: false
        })
        $(this).attr("href", `./index.html`)
        //取消a标签的跳转
        // event.preventDefault()
    })


    /**  分页 */
    let getTopicList = function (page) {
        $.ajax({
            url: "../topic",
            type: "post",
            data: {
                method: "getPageDtoByCId",
                page,
                cId
            }
        }).then(res => {
                console.log(res)
                let rows = res.list.length
                // console.log(res.totalRecord, res.pageSize)
                // console.log(res)
                laypage.render({
                    elem: 'pageElement', //注意，这里的 test1 是 ID，不用加 # 号
                    count: res.totalRecord, //数据总数，从服务端得到
                    limit: res.pageSize, //每页的最大展示条数
                    layout: ["prev", "page", "next", "refresh", "skip"],
                    curr: page,
                    jump: function (obj, first) {
                        // console.log('curr:', obj.curr)
                        if (!first) {
                            // console.log('!first')
                            getTopicList(obj.curr)
                        }
                    }
                })
                $("#topic").html("")
                for (let i = 0; i < rows; i++) {
                    let topicItem = $(`
                        <div class="topicItem" topicId=${res.list[i].id}>
                            <div>
                               <h1>${res.list[i].title}</h1>
                            </div>
                            <hr >
                            <div>
                                ${(res.list[i].content).substring(0, 200) + '">'}...
                            </div>
                            <hr >
                            <div style="display: flex;justify-content: flex-end;align-items: baseline">
                                浏览量：<span style="color: red">${res.list[i].pv}</span>
                            </div>
                        </div>
`)
                    $("#topic").append(topicItem)
                }
            }
        )
    }
//得到第一页的数据
    getTopicList(1)

    /** 移入时有背景色 */
    $("#topic").on("mouseenter", ".topicItem", function () {
        // console.log('移入了')
        $(this).css({"background-color": "rgb(248, 248, 248)"})
    })
    /** 鼠标移出时删去背景色 */
    $("#topic").on("mouseleave", ".topicItem", function () {
        // console.log('移出了')
        $(this).css({"background-color": ""})
    })

    /**topicItem的点击跳转页面 */
    $("#topic").on("click", ".topicItem", function () {
        let topicId = $(this).attr("topicId")
        // console.log(topicId)

        /** 在新窗口中打开页面 */
        window.open(`./topic.html?topicId=${topicId}`)
        // window.location.href = `./topic.html?topicId=${topicId}`
    })

});