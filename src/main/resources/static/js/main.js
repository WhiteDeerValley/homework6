
function check(its)
{


    if(its.validity.patternMismatch === true)
    {
        its.style.color = "rgb(240, 0, 0)";
    }
    else
    {
        its.style.color = "rgb(0, 240, 0)";
    }
}

function comment(ele){
    //this是dom对象，btn是jquery对象
    let url;
    // alert(user);
    url = "/ajax/getcomments";
    $.ajax({
        url:url,
        type:"GET",
        success: function (result){
            var html = "<div class=\"comments\" >"
                //     html +="<textarea class=\"weiboInput\" title=\"评论输入框\" style=\"overflow: hidden\" cols=\"1\" rows=\"1\">"
                //     html+="</textarea>"
                // html+="<div>"
                //     html+="<button class=\"funToSubmit\">"
                //     html+="提交"
                //     html+="</button>"
                // html+="</div>"
                //
                // html = "<div class=\"exitcomments\" >"
                //     html+= "<span>";
                //         html+"发布者信息";
                //     html+="</span>";
                //     html+= "<span>";
                //         html+"评论内容";
                //     html+="</span>";
                // html+="</div>"
            html+="</div>"
            ele.parentNode.parentNode.parentNode.append(html);
            console.log($(ele).index())

        },
        error: function (jqXHR, textStatus, errorThrown){
        alert('新建或修改错误!');
    }
    })
}

function Order(ele){
    let url;
    url = "/ajax/main";
    var json;
    var method = ele.getAttribute("id");
    $.ajax({
        url:url,
        type:"GET",
        data:{method:method},
        success:function (result){
            console.log(result)
            $("#postTotal").empty()
            $.each(result,function (i,n)
            {
                var xhtml = "<div id=\"posts\" th:each=\"i,iStat:${msgList}\" >"
                    xhtml += "<div class=\"post\" >";
                        xhtml +='<h2 class=\"publisher\" >';
                            if (n.fromId!=null)
                                xhtml+=    n.fromId;
                        xhtml+="</h2>";
                        xhtml+="<div class=\"story\">";
                            xhtml+="<p class=\"publishCon\" th:text = ${i[\"content\"]}>";
                            xhtml+=n.comment;
                            xhtml+="</p>"
                        xhtml+="</div>"
                        xhtml+="<p class=\"date\">"
                        xhtml+="<span class=\"posted\" th:text = ${i[\"createDate\"]}>";
                            xhtml+=n.createDate;
                        xhtml+="</span>"
                        xhtml+="</p>"
                        xhtml+="<p class=\"file\">"
                            xhtml+="<a href=\"###\" class=\"comments\"  onclick=\"comment(this)\">";
                            xhtml+=n.commentNum;
                            xhtml+="</a>";
                            xhtml+="<a href=\"#\" class=\"permalink\" th:text = ${i[\"aggreeNum\"]}>"
                            xhtml+=n.aggreeNum;
                            xhtml+="</a>"
                        xhtml+="</p>"
                    xhtml+="</div>"
                xhtml+="<div>";
                console.log(xhtml);
                $("#postTotal").append(xhtml);
            });
        },
        error: function (jqXHR, textStatus, errorThrown){
            alert('新建或修改错误!');
        }
    })
    console.log(json)

}

$(document).ready(


    $('.loginBtn').on('click',function(){
        //事件处理程序
        save_method = 'login';
        $("#login_model").modal("show");
        $("#passWordAgain").css("display","none");
    }),
    $(".logupBtn").on('click',function(){

        save_method = 'logup';
        $("#login_model").modal("show")
    }),
    $("#loginCertain_btn").on('click',function() {

        let url;

        url = "/ajax/login/checkLogin";
        if (save_method === 'login') {
            $.ajax({
                url: url,
                type: "POST",
                data: $('#form').serialize(),
                success: function (result) {
                    //如果成功，隐藏弹出框并重新加载数据
                    if (result === "error") {
                        alert('用户名或密码错误，请重新输入');
                        $("#passWord").val("");
                    } else {
                        $('#login_model').modal('hide');
                        reload_table();
                    }

                },
                error: function (jqXHR, textStatus, errorThrown) {

                    alert('新建或修改错误！');
                }
            })
        }
        else if (save_method === 'logup') {

            //如果格式全部都匹配
            if(document.getElementById("userName").validity.patternMismatch === false && document.getElementById("passWord").validity.patternMismatch === false && document.getElementById("passWordAgain").validity.patternMismatch === false){

                //前后两次输入密码相同
                if($("#passWord").val() === $("#passWordAgain").val())
                {
                    url = "/ajax/logup/checkLogup";
                    $.ajax({
                        url:url,
                        type: "POST",
                        data: $('#form').serialize(),
                        //ajax成功
                        success: function (result){
                            //注册失败
                            if (result === "error")
                            {
                                alert('已存在同名用户或存在非法字符');
                                $("#passWord").val("");
                                $("#passWordAgain").val("")

                            }
                            else if(result === "success")
                            {
                                $('#login_model').modal('hide');
                                $("#passWord").val("");
                                $("#passWordAgain").val("");
                                $("#userName").val("");
                                $("#logupBtn").modal('hide');

                            }
                        },
                        error:function (jqXHR, textStatus, errorThrown){
                            alert('新建或修改错误!');
                        }
                    })

                }
                else
                {
                    alert("前后两次输入密码不相同,请重新输入");
                    $("#passWord").val("");
                    $("#passWordAgain").val("")
                }
            }
            else
            {
                alert('输入格式不正确，请检查后重新输入');
                $("#passWord").val("");
                $("#passWordAgain").val("")

            }
        }
    })
)





