
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
function agree(ele){

    let url;
    url = "/ajax/aggreet"
    let id = ele.parentNode.parentNode.parentNode.getAttribute("id");

    $.ajax({
        url:url,
        type:"GET",
        //其中用户的id自己后端自己从session里面取
        data:{"id":id},
        success: function (result){
            let num = Number(ele.innerHTML) ;
            let ac = Number(result)
            ele.innerHTML = num+ac;
            console.log(result);
            console.log(num);
        },
        error: function (jqXHR, textStatus, errorThrown){
            alert('新建或修改错误!');
        }
    })
}
function comment(ele){
    //this是dom对象，btn是jquery对象
    let url;
    // alert(user);
    url = "/ajax/getcomments";
    let method = "id";
    let id = ele.parentNode.parentNode.parentNode.getAttribute("id");
    // alert(id);
    $.ajax({
        url:url,
        type:"GET",
        data:{"id":id,"method":method},
        success: function (result){




            let parentnodes = ele.parentNode.parentNode.parentNode.parentNode;

            let delcomments = parentnodes.lastElementChild;
            if (delcomments.className==="comments")
            {
                parentnodes.removeChild(delcomments);
            }
            else {
                let tempNode = document.createElement('div');
                tempNode.setAttribute("class", "comments");
                let text = document.createElement("textarea");
                text.setAttribute("class", "weiboInput");
                text.setAttribute("title", "评论输入框");
                text.setAttribute("style", "overflow: hidden");
                text.setAttribute("cols", 1);
                text.setAttribute("rows", 1);
                tempNode.appendChild(text);
                let submit = document.createElement("div");
                let bt = document.createElement("button");
                bt.appendChild(document.createTextNode("提交"));
                bt.setAttribute("class", "funToSubmit");
                submit.appendChild(bt);
                tempNode.appendChild(submit);

                if(result.length > 0)
                {
                    $.each(result,function (i,n) {

                        let exit = document.createElement("div");
                        exit.setAttribute("class", "exitcomments");
                        let th1 = document.createElement("span");
                        th1.appendChild(document.createTextNode(n.name));
                        let th2 = document.createElement("span");
                        th1.appendChild(document.createTextNode(n.comment));
                        exit.appendChild(th1);
                        exit.appendChild(th2);
                        tempNode.appendChild(exit);


                    })
                }
                else
                {
                    text.setAttribute("placeholder","还没有人评论哦");
                }
                parentnodes.append(tempNode);
            }
            // parentnodes.removeChild(delcomments);

        },
        error: function (jqXHR, textStatus, errorThrown){
        alert('新建或修改错误!');
    }
    })
}



function Order(ele){
    let url;
    url = "/ajax/main";
    let json;
    let method = ele.getAttribute("id");

    $.ajax({
        url:url,
        type:"GET",
        data:{method:method},
        success:function (result){

            $("#postTotal").empty();
            $.each(result,function (i,n)
            {

                let posts = document.createElement("div");
                posts.setAttribute("id","posts");
                let post = document.createElement("div");
                post.setAttribute("class","post");
                post.setAttribute("id",n.id);

                let h2T = document.createElement("h2");
                h2T.setAttribute("class","publisher");
                h2T.appendChild(document.createTextNode(n.fromId));
                post.appendChild(h2T);

                let story = document.createElement("div");
                story.setAttribute("class","story");
                let p = document.createElement("p");
                p.setAttribute("class","publishCon");
                p.appendChild(document.createTextNode(n.content));
                story.appendChild(p);
                post.appendChild(story);

                let meta = document.createElement("div");
                let pdate = document.createElement("p");
                pdate.setAttribute("class","date");

                let span_posted = document.createElement("span");
                span_posted.appendChild(document.createTextNode(n.createDate));

                pdate.appendChild(span_posted);
                meta.appendChild(pdate);

                let pfile = document.createElement("p");
                pfile.setAttribute("class","file");

                let a_comments = document.createElement("a");
                a_comments.setAttribute("href","###");
                a_comments.setAttribute("class","comments");
                a_comments.setAttribute("onclick","comment(this)");
                a_comments.appendChild(document.createTextNode(n.commentNum));
                pfile.appendChild(a_comments);
                let a_aggreets = document.createElement("a");
                a_aggreets.setAttribute("href","###");
                a_aggreets.setAttribute("class","agreets");
                a_aggreets.setAttribute("onclick","agree(this)");
                a_aggreets.appendChild(document.createTextNode(n.aggreeNum));
                pfile.appendChild(a_aggreets);

                meta.appendChild(pfile);
                post.appendChild(meta);
                posts.appendChild(post);
                $("#postTotal").append(posts);
            });
        },
        error: function (jqXHR, textStatus, errorThrown){
            alert('新建或修改错误!');
        }
    })


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
                        alert("success");
                        $("#labelUrl").val("Yes");
                        $('#login_model').modal('hide');

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





