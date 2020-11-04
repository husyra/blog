<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/static/tags/taglibs.jsp"%>
<html>
    <head>
        <title>2048游戏</title>
        <meta charset="utf-8"/>
        <link type="text/css" rel="stylesheet" href="${ctx}/static/css/2048.css" />
        <script type="text/javascript" src="${ctx}/static/js/2048.js"></script>
    </head>

    <body onload="load('${User.id}')">

        <input type="hidden" id="dataStr" value='${User.dataStr}'/>

        <p>Score:<span id="score"></span></p>

        <div id="gridPanel">

            <!--背景单元格-->

            <div id="g00" class="grid"></div>

            <div id="g01" class="grid"></div>

            <div id="g02" class="grid"></div>

            <div id="g03" class="grid"></div>



            <div id="g10" class="grid"></div>

            <div id="g11" class="grid"></div>

            <div id="g12" class="grid"></div>

            <div id="g13" class="grid"></div>



            <div id="g20" class="grid"></div>

            <div id="g21" class="grid"></div>

            <div id="g22" class="grid"></div>

            <div id="g23" class="grid"></div>



            <div id="g30" class="grid"></div>

            <div id="g31" class="grid"></div>

            <div id="g32" class="grid"></div>

            <div id="g33" class="grid"></div>

            <!--前景单元格-->

            <div id="c00" class="cell"></div>

            <div id="c01" class="cell"></div>

            <div id="c02" class="cell"></div>

            <div id="c03" class="cell"></div>



            <div id="c10" class="cell"></div>

            <div id="c11" class="cell"></div>

            <div id="c12" class="cell"></div>

            <div id="c13" class="cell"></div>



            <div id="c20" class="cell"></div>

            <div id="c21" class="cell"></div>

            <div id="c22" class="cell"></div>

            <div id="c23" class="cell"></div>



            <div id="c30" class="cell"></div>

            <div id="c31" class="cell"></div>

            <div id="c32" class="cell"></div>

            <div id="c33" class="cell"></div>

        </div>
        <!--Game Over界面-->

        <div id="gameOver">

            <div><!--灰色半透明背景--></div>

            <!--前景小窗-->

            <p>Game Over!<br>

                Score:<span id="finalScore"></span><br>

                <a class="button" id="restart" href="javascript:save();" οnclick="">Try again!</a>

            </p>

        </div>
    </body>
</html>
