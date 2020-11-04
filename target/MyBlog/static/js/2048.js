//使用Task移动格子，有移动动作
function Task(obj,topStep,leftStep){
	this.obj=obj;

	this.topStep=topStep;

	this.leftStep=leftStep;

}

//moveStep方法将当前元素对象移动一步

Task.prototype.moveStep=function(){
	var style=getComputedStyle(this.obj,null);

	var top=parseInt(style.top);

	var left=parseInt(style.left);

	this.obj.style.top=top+this.topStep+"px";

	this.obj.style.left=left+this.leftStep+"px";

}

//清楚元素对象的样式，使其返回原地

Task.prototype.clear=function(){
	this.obj.style.left="";
	this.obj.style.top="";
}



var animation={
	times:10,//每个动画10步完成

	interval:10,//200毫秒迈一步

	timer:null,//保存定时器id的属性

	tasks:[],//保存每次需要移动的任务

	//移动位置
	addTask:function(source,target){
		//00      02

		//console.log(source+","+target);

		var sourceDiv=document.getElementById("c"+source);

		var targetDiv= document.getElementById("c"+target);

		var sourceStyle=getComputedStyle(sourceDiv);

		var targetStyle=getComputedStyle(targetDiv);

		var topStep=(parseInt(targetStyle.top) - parseInt(sourceStyle.top))/this.times;

		var leftStep=(parseInt(targetStyle.left) - parseInt(sourceStyle.left))/this.times;

		var task=new Task(sourceDiv,topStep,leftStep);

		this.tasks.push(task);

	},

	start:function(){

		this.timer=setInterval(function(){
			for(var i=0;i<animation.tasks.length;i++){
				animation.tasks[i].moveStep();
			}

			animation.times--;

			if(animation.times==0){
				for(var i=0;i<animation.tasks.length;i++){
					animation.tasks[i].clear();
				}

				clearInterval(animation.timer);

				animation.timer=null;

				animation.tasks=[];

				animation.times=10;

			}

		},this.interval);

	}

}

var game={
	data:[],

	score:0,

	state:1,

	GAME_OVER:0, RUNNING:1, PLAYING:2,//动画正在播放中

	init:function(){

		this.data=[[0,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0]];

		this.score=0;

		this.state=this.RUNNING;

		var div=document.getElementById("gameOver");

		//修改div的style.display

		div.style.display="none";

		//开始时有两个数据
		this.randomNum();
		this.randomNum();

		this.updateView();

	},

	isFull:function(){
		for(var row=0;row<4;row++){
			for(var col=0;col<4;col++){
				if(this.data[row][col]==0){
					return false;

				}

			}

		}
		return true;
	},

	randomNum:function(){
		//console.log("game.randomNum");
		if(this.isFull()){
			return;
		}

		while(true){
			var row=Math.floor(Math.random()*4);

			var col=Math.floor(Math.random()*4);

			if(this.data[row][col]==0){
				this.data[row][col]= Math.random()<0.5?2:4;
				break;
			}

		}
		//console.log("data="+this.data);
	},

	canLeft:function(){

		//遍历每个元素(最左侧列除外)
		for(var row=0;row<4;row++){
			for(var col=1;col<4;col++){
				//只要发现任意元素左侧值==0或当前值==左侧值(与自身相等值的元素，也可左移相加)
				if(this.data[row][col]!=0){
					if(this.data[row][col-1]==0||this.data[row][col-1]==this.data[row][col]){
						return true;

					}

				}

			}

		}//如果循环正常结束，

		return false;

	},

	moveLeft:function(){
		if(this.canLeft()){//先判断能否左移

			/* 向左移动，是行的移动，行要叠加，并且先从最左边开始移动并叠加*/
			for(var row=0;row<4;row++){

				this.moveLeftInRow(row);
			}

			this.state=this.PLAYING;

			animation.start();

			setTimeout(function(){
				game.state=game.RUNNING;

				game.randomNum();

				game.updateView();

			},animation.times*animation.interval);

		}

	},

	moveLeftInRow:function(row){
		for(var col=0;col<=2;col++){
			//从最左侧开始循环前3列，找右边的格子， 存在右边值则合并
			var nextCol=this.getNextRight(row,col);

			if(nextCol==-1){ //右边没有不为空的数值，则不需关
				break;
			}else{
				//目前的格子为空，则将右边的格子移动到左边
				if(this.data[row][col]==0){
					this.data[row][col]=this.data[row][nextCol];

					//并将已经移位的右边的值改成空
					this.data[row][nextCol]=0;

					animation.addTask(""+row+nextCol,""+row+col);

					//此处为什么要减-1?
					col--;

					//相邻两个格子数值相同，则相加，也就是*2
				}else if(this.data[row][col]==this.data[row][nextCol]){
					this.data[row][col]*=2;

					//分数相加
					this.score+=this.data[row][col];

					this.data[row][nextCol]=0;

					animation.addTask(""+row+nextCol,""+row+col);

				}

			}

		}

	},

	getNextRight:function(row,col){
		for(var i=col+1;i<4;i++){
			//从目标格子的左边第一个开始循环，找到数值不为空的返回其下标
			if(this.data[row][i]!=0){
				return i;
			}

		}

		return -1;

	},

	/*将游戏数据整体更新到页面上*/
	updateView:function(){
		//console.log("game.updateView");
		//Step1：遍历二维数组中每个元素
		for(var row=0;row<4;row++){
			for(var col=0;col<4;col++){
				//Step2：找到和当前元素对应的div
				//     拼div的id: c+row+col
				var div=document.getElementById("c"+row+col);

				//Step3：将当前元素的值放入div中
				//    div.innerHTML=?
				//    如果当前值==0，放入""
				//           否则放入当前值
				div.innerHTML=this.data[row][col]==0?"":this.data[row][col];

				//Step4: 根据当前元素值修改div样式类
				//    div.className="类名";
				//    如果当前值==0,className="cell"
				//    否则className="cell n"+当前值
				div.className=this.data[row][col]==0?"cell":"cell n"+this.data[row][col];

			}

		}

		/*将分数放入span*/

		var span=document.getElementById("score");

		span.innerHTML=this.score;

		/*判断游戏结束*/

		if(this.isGameOver()){
			this.state=this.GAME_OVER;

			//显示游戏结束div

			//找到gameOverdiv

			var div=document.getElementById("gameOver");

			var finalScore= document.getElementById("finalScore");

			finalScore.innerHTML=this.score;

			//显示div

			div.style.display="block";

		}

	},

	canRight:function(){/*判断能否右移*/

		for(var row=0;row<4;row++){
			for(var col=0;col<3;col++){
				if(this.data[row][col]!=0){
					if(this.data[row][col+1]==0||this.data[row][col]==this.data[row][col+1]){
						return true;
					}
				}

			}

		}
		return false;
	},

	moveRight:function(){/*向右移动所有行*/

		if(this.canRight()){
			for(var row=0;row<4;row++){
				this.moveRightInRow(row);
			}

			this.state=this.PLAYING;

			animation.start();

			setTimeout(function(){
				game.state=game.RUNNING;

				game.randomNum();

				game.updateView();

			},animation.times*animation.interval);

		}

	},

	moveRightInRow:function(row){/*右移当前行*/

		//从右向左遍历检查，(最左边元素除外)
		for(var col=3;col>0;col--){
			var nextCol=this.getNextLeft(row,col);

			if(nextCol==-1){
				break;
			}else{
				if(this.data[row][col]==0){
					this.data[row][col]=this.data[row][nextCol];

					this.data[row][nextCol]=0;

					animation.addTask(""+row+nextCol,""+row+col);

					col++;

				}else if(this.data[row][col]==this.data[row][nextCol]){
					this.data[row][col]*=2;

					this.score+=this.data[row][col];

					this.data[row][nextCol]=0;

					animation.addTask(""+row+nextCol,""+row+col);

				}

			}

		}

	},

	getNextLeft:function(row,col){
		//从当前位置向左，找下一个不为0的数

		for(var i=col-1;i>=0;i--){
			if(this.data[row][i]!=0){
				return i;
			}

		}return -1;

	},

	canUp:function(){
		for(var row=1;row<4;row++){
			for(var col=0;col<4;col++){
				if(this.data[row][col]!=0){
					if(this.data[row-1][col]==0||this.data[row][col]==this.data[row-1][col]){
						return true;
					}

				}

			}

		}
		return false;
	},

	moveUp:function(){
		if(this.canUp()){
			for(var col=0;col<4;col++){
				this.moveUpInCol(col);
			}

			this.state=this.PLAYING;

			animation.start();

			setTimeout(function(){
				game.state=game.RUNNING;

				game.randomNum();

				game.updateView();

			},animation.times*animation.interval);

		}

	},

	moveUpInCol:function(col){
		for(var row=0;row<3;row++){
			var nextRow=this.getNextDown(row,col);

			if(nextRow==-1){
				break;
			}else{
				if(this.data[row][col]==0){
					this.data[row][col]=this.data[nextRow][col];

					this.data[nextRow][col]=0;

					animation.addTask(""+nextRow+col,""+row+col);

					row--;

				}else if(this.data[row][col]==this.data[nextRow][col]){
					this.data[row][col]*=2;

					this.score+=this.data[row][col];

					this.data[nextRow][col]=0;

					animation.addTask(""+nextRow+col,""+row+col);

				}

			}

		}

	},

	getNextDown:function(row,col){
		for(var i=row+1;i<4;i++){
			if(this.data[i][col]!=0){
				return i;
			}
		}
		return -1;
	},

	canDown:function(){
		for(var row=0;row<3;row++){
			for(var col=0;col<4;col++){
				if(this.data[row][col]!=0){
					if(this.data[row+1][col]==0||this.data[row][col]==this.data[row+1][col]){
						return true;
					}

				}

			}

		}
		return false;
	},

	moveDown:function(){
		if(this.canDown()){
			for(var col=0;col<4;col++){
				this.moveDownInCol(col);
			}

			this.state=this.PLAYING;

			animation.start();

			setTimeout(function(){
				game.state=game.RUNNING;

				game.randomNum();

				game.updateView();

			},animation.times*animation.interval);

		}

	},

	moveDownInCol:function(col){
		for(var row=3;row>0;row--){
			var nextRow=this.getNextUp(row,col);

			if(nextRow==-1){
				break;
			}else{
				if(this.data[row][col]==0){
					this.data[row][col]=this.data[nextRow][col];

					this.data[nextRow][col]=0;

					animation.addTask(""+nextRow+col,""+row+col);

					row++;

				}else if(this.data[row][col]==this.data[nextRow][col]){
					this.data[row][col]*=2;

					this.score+=this.data[row][col];

					this.data[nextRow][col]=0;

					animation.addTask(""+nextRow+col,""+row+col);

				}

			}

		}

	},

	getNextUp:function(row,col){
		for(var i=row-1;i>=0;i--){
			if(this.data[i][col]!=0){
				return i;
			}
		}
		return -1;
	},

	isGameOver:function(){/*判断游戏是否结束*/

		for(var row=0;row<4;row++){
			for(var col=0;col<4;col++){
				if(this.data[row][col]==0){
					return false;
				}

				if(col<3){/*检查右侧相邻*/
					if(this.data[row][col]==this.data[row][col+1]){
						return false;
					}

				}

				if(row<3){/*检查下方相邻*/
					if(this.data[row][col]==this.data[row+1][col]){
						return false;
					}

				}

			}

		}

		return true;
	}

}

function load(userId){

	if(userId == null || userId == ''){
		var is = confirm('未登录，无法保存游戏进度哦');
		if(!is){
			return;
		}
	}

	//如果用户存在正在玩的游戏，则回显
	var dataStr = $("#dataStr").val();
	if(dataStr==null || dataStr==''){
		//开始新的游戏
		//初始化页面数据
		game.init();
	}else{
		//加载已保存的游戏布局
		console.log("dataStr=="+dataStr);
		var dataObj = $.parseJSON(dataStr);
		var data = dataObj.data;
		var score = dataObj.score;

		game.state=game.RUNNING;
		//此处需要将游戏数据填充进去。
		//保存的数据为：c00,c01,c02,c03, c10,c11,c12,c13, c20,c21,c22,c23, c30,c31,c32,c33
		//data数据为：[c00,c01,c02,c03], [c10,c11,c12,c13], [c20,c21,c22,c23], [c30,c31,c32,c33]
		var arr1 = data.split(",");

		var gameData = [[0,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0]];
		var j=0;
		var k=0;
		for(var i=0; i<arr1.length; i++){
			if(i>3 && i<8){
				j=1;
			}else if(i>7 && i<12){
				j=2;
			}else if(i>11){
				j=3;
			}
			if(i % 4 == 0){
				k=0;
			}
			gameData[j][k]=arr1[i];
			k++;
		}

		console.log("gameData=="+gameData);
		//数组赋值
		game.data = [].concat(gameData);
		game.score = score;
		game.updateView();
	}

	document.onkeydown=function(event){

		//var event=window.event||arguments[0];
		//var e = event || window.event || arguments.callee.caller.arguments[0];
		if(event.keyCode==37){
			game.moveLeft();
		}else if(event.keyCode==39){
			game.moveRight();
		}else if(event.keyCode==38){
			game.moveUp();
		}else if(event.keyCode==40){
			game.moveDown();
		}

	}

}

function save(id){

	var s = confirm("是否保存游戏进度？");
	if(!s){
		game.init();
		return;
	}

	if(id == null || id == ''){
		//未登录，重新开始游戏
		game.init();
		return;
	}

	var highScore = game.score;
	//获取用户当前最高分数
	var dataStr = $("#dataStr").val();
	//console.log("dataStr="+dataStr);

	if(dataStr!=null && dataStr!=''){
		//比较当前分数，如果比最高分数高，则将当前分数存入到最高分数
		var dataObj = $.parseJSON(dataStr);
		if(Number(game.score)< Number(dataObj.highScore)){
			highScore = dataObj.highScore;
		}
	}

	//拼接记录数据{"highScore":"", "data":""}
	var finalData ='{"score":'+game.score+',';
	finalData +='"data":"'+game.data+'",';
	finalData +='"highScore":'+highScore+'}';
	//console.log("finalData="+finalData);

	//保存游戏进度
	$.ajax({
		url:"/MyBlog/user/saveGame.do",
		type:"post",
		data:{"userId":id ,"data":finalData},
		dataType:'json',
		success:function (result){
			alert(result.resultMsg);
			//重新开始游戏
			//game.init();
		},
		error:function () {
			alert("*系统异常");
		}
	});

}