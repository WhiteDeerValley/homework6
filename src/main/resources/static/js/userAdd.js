function check(id)
{
    var elem = document.getElementById(id);
    var pattern = elem.pattern;
    var infor = elem.value;
    var regex = new RegExp('^' + pattern + '$'); //虽然正则表达式也是字符型，但和字符串不是一个类型
                                                 //而且html中的pattern会自动默认全匹配，但是这里面不会，
                                                 //需要手动添加^ 和 $
    var re = regex.exec(infor);
    var temp = null;

    if ('UName' == id)
        temp = document.getElementById('r1');
    else if ('UTel' == id)
        temp = document.getElementById('r2');
    else if ('UEmail' == id)
        temp = document.getElementById('r3');
    else if ('UAdd' == id)
        temp = document.getElementById('r4');
    else if ('Uqq' == id)
        temp = document.getElementById('r5');

    if (null != re)
    {
        temp.innerHTML = "√格式正确";
        temp.style.color = "rgb(0, 240, 0)";
    }
    else if (null == re)
    {
        temp.innerHTML = "×格式错误";
        temp.style.color = "rgb(240, 0, 0)";
    }
}