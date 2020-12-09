$('.funToSubmit').on("click",function (){

    const content = $('.weiboInput').val();

    if (content === "")
        alert("Please check the Input");
    else {
        url = "/ajax/main/publish"
        // alert(content)
        //我们需要发布消息，并且获得用户信息
        $.ajax({
            url: url,
            type: "POST",
            data: {contentText :content},
            //获取用户信息成功
            success: function (result) {
                if ("passenger" === result) {

                }
                alert(result)

            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert('新建或修改错误！');
            }
        })

    }
    // alert("Success!");
});
// $.getJSON("ajax/main?ation=main&h=?",function (result){
//     $('.publisher').html(result);
// });