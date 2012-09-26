<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
body {
	font-size: 12px;
	height:100%;
}

.l-table-edit {
	
}

.l-table-edit-td {
	padding: 4px;
}

.l-button-submit,.l-button-test {
	width: 80px;
	float: left;
	margin-left: 10px;
	padding-bottom: 2px;
}
</style>
	

<script type='text/javascript'>
</script>
</head>
<body style="width: 95%; height: 95%;" onload="Taskconfparse();">




<table style="width: 100%; height: 100%; vertical-align: top"
	   border="0" cellpadding="2" cellspacing="2" width="90%" align="center">

<tr>
	<td><input  id="taskDef" type="hidden" value='<%=request.getParameter("taskConf")==null?"":request.getParameter("taskConf")%>'/>
		<input
			type="radio" id="taskconf.type1" name="taskconf.type" value="month"
			onclick="Reset(this);" checked/>每月<input type="radio"
															id="taskconf.type2" name="taskconf.type" value="week"
															onclick="Reset(this);"/>每周
	</td>
</tr>
<tr id="dateselect">
	<td><select id="date">
		<option value="*">每天</option>
		<option value="1">1号</option>
		<option value="2">2号</option>
		<option value="3">3号</option>
		<option value="4">4号</option>
		<option value="5">5号</option>
		<option value="6">6号</option>
		<option value="7">7号</option>
		<option value="8">8号</option>
		<option value="9">9号</option>
		<option value="10">10号</option>
		<option value="11">11号</option>
		<option value="12">12号</option>
		<option value="13">13号</option>
		<option value="14">14号</option>
		<option value="15">15号</option>
		<option value="16">16号</option>
		<option value="17">17号</option>
		<option value="18">18号</option>
		<option value="19">19号</option>
		<option value="20">20号</option>
		<option value="21">21号</option>
		<option value="22">22号</option>
		<option value="23">23号</option>
		<option value="24">24号</option>
		<option value="25">25号</option>
		<option value="26">26号</option>
		<option value="27">27号</option>
		<option value="28">28号</option>
		<option value="29">29号</option>
		<option value="30">30号</option>
		<option value="31">31号</option>
		<option value="L">月末</option>
	</select> &nbsp;&nbsp;选择每月某一天
	</td>
</tr>
<tr id="dayselect" style="display:none">
	<td width="20%"><select id="day">
		<option value="*">每天</option>
		<option value="2">周一</option>
		<option value="3">周二</option>
		<option value="4">周三</option>
		<option value="5">周四</option>
		<option value="6">周五</option>
		<option value="7">周六</option>
		<option value="1">周日</option>
	</select> &nbsp;&nbsp;星期日
	</td>
</tr>
<tr>
	<td>
		<table border="0" cellspacing=0 cellpadding=0>
			<tr>
				<td><input type="radio" id="hourtype1" name="hourtype"
						   value="hourtype1" onclick="Resethour(this);"/></td>
				<td id="hour1">每小时</td>
			</tr>
			<tr>
				<td><input type="radio" id="hourtype2" name="hourtype"
						   value="hourtype2" onclick="Resethour(this);"/></td>
				<td id="hour2">固定小时<input type="text" id="hourexpress2"
												size="5%" maxlength="2" onkeyup="checktime_hour(this)"/> 小时
				</td>
			</tr>
			<tr>
				<td><input type="radio" id="hourtype3" name="hourtype"
						   value="hourtype3" onclick="Resethour(this);"/></td>
				<td id="hour3">从 <select id="hourexpress31"
										 onchange="hourconnect()">
					<option value="0">0</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
					<option value="13">13</option>
					<option value="14">14</option>
					<option value="15">15</option>
					<option value="16">16</option>
					<option value="17">17</option>
					<option value="18">18</option>
					<option value="19">19</option>
					<option value="20">20</option>
					<option value="21">21</option>
					<option value="22">22</option>
					<option value="23">23</option>
				</select> 时,到 <select id="hourexpress32" onchange="hourcheck()">
					<option value="0">0</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
					<option value="13">13</option>
					<option value="14">14</option>
					<option value="15">15</option>
					<option value="16">16</option>
					<option value="17">17</option>
					<option value="18">18</option>
					<option value="19">19</option>
					<option value="20">20</option>
					<option value="21">21</option>
					<option value="22">22</option>
					<option value="23">23</option>
				</select> 时
				</td>
			</tr>
			<tr>
				<td><input type="radio" id="hourtype4" name="hourtype"
						   value="hourtype4" onclick="Resethour(this);"/></td>
				<td id="hour4">从 <select id="hourexpress41">
					<option value="0">0</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
					<option value="13">13</option>
					<option value="14">14</option>
					<option value="15">15</option>
					<option value="16">16</option>
					<option value="17">17</option>
					<option value="18">18</option>
					<option value="19">19</option>
					<option value="20">20</option>
					<option value="21">21</option>
					<option value="22">22</option>
					<option value="23">23</option>
				</select> 时 开始，前 <input type="text" id="hourexpress42" size="2%"
									   onkeyup="checktime_hour(this)"/>
					每小时循环
				</td>
			</tr>
		</table>
	</td>
</tr>
<tr>
<td>
<table border="0" cellspacing=0 cellpadding=0>
<tr>
	<td><input type="radio" id="minutetype1" name="minutetype"
			   value="minutetype1" onclick="Resetminute(this);"/></td>
	<td id="minute1">每分钟循环</td>
</tr>
<tr>
	<td><input type="radio" id="minutetype2" name="minutetype"
			   value="minutetype2" onclick="Resetminute(this);"/></td>
	<td id="minute2">固定分钟<input type="text"
									  id="minuteexpress2" size="5%" maxlength="2"
									  onkeyup="checktime_mi(this)"/> 分
	</td>
</tr>
<tr>
	<td><input type="radio" id="minutetype3" name="minutetype"
			   value="minutetype3" onclick="Resetminute(this);"/></td>
	<td id="minute3">从<select id="minuteexpress31"
							   onchange="minuteconnect()">
		<option value="0">0</option>
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>
		<option value="6">6</option>
		<option value="7">7</option>
		<option value="8">8</option>
		<option value="9">9</option>
		<option value="10">10</option>
		<option value="11">11</option>
		<option value="12">12</option>
		<option value="13">13</option>
		<option value="14">14</option>
		<option value="15">15</option>
		<option value="16">16</option>
		<option value="17">17</option>
		<option value="18">18</option>
		<option value="19">19</option>
		<option value="20">20</option>
		<option value="21">21</option>
		<option value="22">22</option>
		<option value="23">23</option>
		<option value="24">24</option>
		<option value="25">25</option>
		<option value="26">26</option>
		<option value="27">27</option>
		<option value="28">28</option>
		<option value="29">29</option>
		<option value="30">30</option>
		<option value="31">31</option>
		<option value="32">32</option>
		<option value="33">33</option>
		<option value="34">34</option>
		<option value="35">35</option>
		<option value="36">36</option>
		<option value="37">37</option>
		<option value="38">38</option>
		<option value="39">39</option>
		<option value="40">40</option>
		<option value="41">41</option>
		<option value="42">42</option>
		<option value="43">43</option>
		<option value="44">44</option>
		<option value="45">45</option>
		<option value="46">46</option>
		<option value="47">47</option>
		<option value="48">48</option>
		<option value="49">49</option>
		<option value="50">50</option>
		<option value="51">51</option>
		<option value="52">52</option>
		<option value="53">53</option>
		<option value="54">54</option>
		<option value="55">55</option>
		<option value="56">56</option>
		<option value="57">57</option>
		<option value="58">58</option>
		<option value="59">59</option>
	</select> 分,到<select id="minuteexpress32" onchange="minutecheck()">
		<option value="0">0</option>
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>
		<option value="6">6</option>
		<option value="7">7</option>
		<option value="8">8</option>
		<option value="9">9</option>
		<option value="10">10</option>
		<option value="11">11</option>
		<option value="12">12</option>
		<option value="13">13</option>
		<option value="14">14</option>
		<option value="15">15</option>
		<option value="16">16</option>
		<option value="17">17</option>
		<option value="18">18</option>
		<option value="19">19</option>
		<option value="20">20</option>
		<option value="21">21</option>
		<option value="22">22</option>
		<option value="23">23</option>
		<option value="24">24</option>
		<option value="25">25</option>
		<option value="26">26</option>
		<option value="27">27</option>
		<option value="28">28</option>
		<option value="29">29</option>
		<option value="30">30</option>
		<option value="31">31</option>
		<option value="32">32</option>
		<option value="33">33</option>
		<option value="34">34</option>
		<option value="35">35</option>
		<option value="36">36</option>
		<option value="37">37</option>
		<option value="38">38</option>
		<option value="39">39</option>
		<option value="40">40</option>
		<option value="41">41</option>
		<option value="42">42</option>
		<option value="43">43</option>
		<option value="44">44</option>
		<option value="45">45</option>
		<option value="46">46</option>
		<option value="47">47</option>
		<option value="48">48</option>
		<option value="49">49</option>
		<option value="50">50</option>
		<option value="51">51</option>
		<option value="52">52</option>
		<option value="53">53</option>
		<option value="54">54</option>
		<option value="55">55</option>
		<option value="56">56</option>
		<option value="57">57</option>
		<option value="58">58</option>
		<option value="59">59</option>
	</select> 分
	</td>
</tr>
<tr>
	<td><input type="radio" id="minutetype4" name="minutetype"
			   value="minutetype4" onclick="Resetminute(this);"/></td>
	<td id="minute4">从<select id="minuteexpress41">
		<option value="0">0</option>
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>
		<option value="6">6</option>
		<option value="7">7</option>
		<option value="8">8</option>
		<option value="9">9</option>
		<option value="10">10</option>
		<option value="11">11</option>
		<option value="12">12</option>
		<option value="13">13</option>
		<option value="14">14</option>
		<option value="15">15</option>
		<option value="16">16</option>
		<option value="17">17</option>
		<option value="18">18</option>
		<option value="19">19</option>
		<option value="20">20</option>
		<option value="21">21</option>
		<option value="22">22</option>
		<option value="23">23</option>
		<option value="24">24</option>
		<option value="25">25</option>
		<option value="26">26</option>
		<option value="27">27</option>
		<option value="28">28</option>
		<option value="29">29</option>
		<option value="30">30</option>
		<option value="31">31</option>
		<option value="32">32</option>
		<option value="33">33</option>
		<option value="34">34</option>
		<option value="35">35</option>
		<option value="36">36</option>
		<option value="37">37</option>
		<option value="38">38</option>
		<option value="39">39</option>
		<option value="40">40</option>
		<option value="41">41</option>
		<option value="42">42</option>
		<option value="43">43</option>
		<option value="44">44</option>
		<option value="45">45</option>
		<option value="46">46</option>
		<option value="47">47</option>
		<option value="48">48</option>
		<option value="49">49</option>
		<option value="50">50</option>
		<option value="51">51</option>
		<option value="52">52</option>
		<option value="53">53</option>
		<option value="54">54</option>
		<option value="55">55</option>
		<option value="56">56</option>
		<option value="57">57</option>
		<option value="58">58</option>
		<option value="59">59</option>
	</select>分 开始，前 <input type="text" id="minuteexpress42" size="2%"
						   onkeyup="checktime_mi(this)"/>
		每分钟循环
	</td>
</tr>
</table>
</td>
</tr>
</table>


</body>


<script defer>
function $()
{
	var elements = new Array();
	for (var i = 0; i < arguments.length; i++)
	{
		var element = arguments[i];
		if (typeof element == 'string')
			element = document.getElementById(element);
		if (arguments.length == 1)
			return element;
		elements.push(element);
	}
	return elements;
}

function DoSave()
{

	var newhour = "0";
	var newminute = "0";
	var newdate = $("date").value;
	var newday = $("day").value;

	if ($("hourtype1").checked) {
		newhour = "*";
	}
	if ($("hourtype2").checked) {
		if (!limit($("hourexpress2"), /^[0-9,]{0,30}$/)) {
			alert("填写错误");
			$("hourexpress2").focus();
			return false;
		}
		if ($("hourexpress2").value == "") {
			alert("填写错误");
			$("hourexpress2").focus();
			return false;
		}
		if ($("hourexpress2").value.charAt(0) == ",") {
			alert("填写错误");
			$("hourexpress2").focus();
			return false;
		}
		var i = $("hourexpress2").value.length;
		if ($("hourexpress2").value.charAt(i - 1) == ",") {
			alert("填写错误");
			$("hourexpress2").focus();
			return false;
		}
		var hourtype2detail = $("hourexpress2").value.split(",");
		for (j = 0; j < hourtype2detail.length; j++) {
			if (hourtype2detail[j] >= 24) {
				alert("填写错误");
				$("hourexpress2").focus();
				return false;
			}
		}
		newhour = $("hourexpress2").value;
	}
	if ($("hourtype3").checked) {
		var i = parseInt($("hourexpress31").value);
		var j = parseInt($("hourexpress32").value);
		if (j <= i) {
			alert("填写错误");
			$("hourexpress32").focus();
			return false;
		}
		newhour = $("hourexpress31").value + "-" + $("hourexpress32").value;
	}
	if ($("hourtype4").checked) {
		if (!limit($("hourexpress42"), /^[0-9,]{0,30}$/)) {
			alert("填写错误");
			$("hourexpress42").focus();
			return false;
		}
		if ($("hourexpress42").value == "") {
			alert("填写错误");
			$("hourexpress42").focus();
			return false;
		}
		if ($("hourexpress42").value.charAt(0) == ",") {
			alert("填写错误");
			$("hourexpress42").focus();
			return false;
		}
		var i = $("hourexpress42").value.length;
		if ($("hourexpress42").value.charAt(i - 1) == ",") {
			alert("填写错误");
			$("hourexpress42").focus();
			return false;
		}
		var hourtype42detail = $("hourexpress42").value.split(",");
		for (j = 0; j < hourtype42detail.length; j++) {
			if (hourtype42detail[j] >= 24) {
				alert("填写错误");
				$("hourexpress42").focus();
				return false;
			}
		}
		newhour = $("hourexpress41").value + "/" + $("hourexpress42").value;
	}
	if ($("minutetype1").checked) {
		newminute = "*";
	}
	if ($("minutetype2").checked) {
		if (!limit($("minuteexpress2"), /^[0-9,]{0,30}$/)) {
			alert("填写错误");
			$("minuteexpress2").focus();
			return false;
		}
		if ($("minuteexpress2").value == "") {
			alert("填写错误");
			$("minuteexpress2").focus();
			return false;
		}
		if ($("minuteexpress2").value.charAt(0) == ",") {
			alert("填写错误");
			$("minuteexpress2").focus();
			return false;
		}
		var i = $("minuteexpress2").value.length;
		if ($("minuteexpress2").value.charAt(i - 1) == ",") {
			alert("填写错误");
			$("minuteexpress2").focus();
			return false;
		}
		var minutetype2detail = $("minuteexpress2").value.split(",");
		for (j = 0; j < minutetype2detail.length; j++) {
			if (minutetype2detail[j] >= 60) {
				alert("填写错误");
				$("minuteexpress2").focus();
				return false;
			}
		}
		newminute = $("minuteexpress2").value;
	}
	if ($("minutetype3").checked) {
		var i = parseInt($("minuteexpress31").value);
		var j = parseInt($("minuteexpress32").value);
		if (j <= i) {
			alert("分钟结束点必须大于开始点！");
			$("minuteexpress32").focus();
			return false;
		}
		newminute = $("minuteexpress31").value + "-" + $("minuteexpress32").value;
	}
	if ($("minutetype4").checked) {
		if (!limit($("minuteexpress42"), /^[0-9,]{0,30}$/)) {
			alert("填写错误");
			$("minuteexpress42").focus();
			return false;
		}
		if ($("minuteexpress42").value == "") {
			alert("填写错误");
			$("minuteexpress42").focus();
			return false;
		}
		if ($("minuteexpress42").value.charAt(0) == ",") {
			alert("填写错误");
			$("minuteexpress42").focus();
			return false;
		}
		var i = $("minuteexpress42").value.length;
		if ($("minuteexpress42").value.charAt(i - 1) == ",") {
			alert("填写错误");
			$("minuteexpress42").focus();
			return false;
		}
		var minutetype42detail = $("minuteexpress42").value.split(",");
		for (j = 0; j < minutetype42detail.length; j++) {
			if (minutetype42detail[j] >= 60) {
				alert("分钟超过60");
				$("minuteexpress42").focus();
				return false;
			}
		}
		newminute = $("minuteexpress41").value + "/" + $("minuteexpress42").value;
	}
	if ( $("taskconf.type1").checked) {
		$("taskDef").value = "0 " + newminute + " " + newhour + " " + newdate + " * ? *";
	} else {
		$("taskDef").value = "0 " + newminute + " " + newhour + " ? * " + newday + " *";
	}
	return true;
}

function hourconnect()
{
	if ($("hourtype3").checked) {
		var i = parseInt($("hourexpress31").value) + 1;
		if (i < 24) {
			$("hourexpress32").getElementsByTagName("option")[i].selected = true;
		} else {
			$("hourexpress32").getElementsByTagName("option")[i - 1].selected = true;
		}
	}
}

function hourcheck()
{
	if ($("hourtype3").checked) {
		var i = parseInt($("hourexpress31").value);
		var j = parseInt($("hourexpress32").value);
		if (j <= i) {
			alert("填写错误");
			if (i == 23) {
				$("hourexpress32").value = i
			} else {
				$("hourexpress32").value = i + 1;
			}
		}
	}
}

function minuteconnect()
{
	if ($("minutetype3").checked) {
		var i = parseInt($("minuteexpress31").value) + 1;
		if (i < 60) {
			$("minuteexpress32").getElementsByTagName("option")[i].selected = true;
		} else {
			$("minuteexpress32").getElementsByTagName("option")[i - 1].selected = true;
		}
	}
}

function minutecheck()
{
	if ($("minutetype3").checked) {
		var i = parseInt($("minuteexpress31").value);
		var j = parseInt($("minuteexpress32").value);
		if (j <= i) {
			alert("填写错误");
			if (i == 59) {
				$("minuteexpress32").value = i
			} else {
				$("minuteexpress32").value = i + 1;
			}
		}
	}
}

function Taskconfparse()
{

	var taskconf = $("taskDef").value;
	if (taskconf != "") {

		var sp = taskconf.substring(1, 2);
		var conf = taskconf.split(sp);
		//	var second=conf[0];暂不考虑秒的设置
		//	var year=conf[6];暂不考虑年的设置
		var minute = conf[1];
		var hour = conf[2];
		var date = conf[3];
		var month = conf[4];
		var day = conf[5];
		//显示当Hours的值是*时，
		if (hour.indexOf("*") != -1 && hour.indexOf("*/") == -1) {

			$("hourtype1").checked = true;
			$("hour2").disabled = true;
			$("hour3").disabled = true;
			$("hour4").disabled = true;
		}
		//显示当hours的值中有/时，
		if (hour.indexOf("/") != -1) {
			$("hourtype4").checked = true;
			$("hour2").disabled = true;
			$("hour3").disabled = true;
			$("hour1").disabled = true;
			var i = hour.indexOf("/");
			$("hourexpress42").value = hour.substring(i + 1, hour.length);
			var j = hour.substring(0, i);
			if (hour.indexOf("*/") != -1) {
				$("hourexpress41").getElementsByTagName("option")[0].selected = true;
			} else {
				$("hourexpress41").getElementsByTagName("option")[j].selected = true;
			}
		}
		//显示当hours的值中有-时，
		if (hour.indexOf("-") != -1) {
			$("hourtype3").checked = true;
			$("hour2").disabled = true;
			$("hour4").disabled = true;
			$("hour1").disabled = true;
			var i = hour.indexOf("-");
			var j = hour.substring(0, i);
			var k = hour.substring(i + 1, hour.length);
			$("hourexpress31").getElementsByTagName("option")[j].selected = true;
			$("hourexpress32").getElementsByTagName("option")[k].selected = true;
		}
		//显示当hour的值为一个数字或者几个用逗号格开的数字，例如1,2,3,4,		
		if (hour.indexOf(",") != -1 || !isNaN(hour)) {
			$("hourtype2").checked = true;
			$("hour3").disabled = true;
			$("hour4").disabled = true;
			$("hour1").disabled = true;
			$("hourexpress2").value = hour;
		}
		//显示当minutes的值是*时，
		if (minute.indexOf("*") != -1 && minute.indexOf("*/") == -1) {
			$("minutetype1").checked = true;
			$("minute2").disabled = true;
			$("minute3").disabled = true;
			$("minute4").disabled = true;
		}
		//显示当minutes的值中有/时，
		if (minute.indexOf("/") != -1) {
			$("minutetype4").checked = true;
			$("minute2").disabled = true;
			$("minute3").disabled = true;
			$("minute1").disabled = true;
			var i = minute.indexOf("/");
			$("minuteexpress42").value = minute.substring(i + 1, minute.length);
			var j = minute.substring(0, i);
			if (minute.indexOf("*/") != -1) {
				$("minuteexpress41").getElementsByTagName("option")[0].selected = true;
			} else {
				$("minuteexpress41").getElementsByTagName("option")[j].selected = true;
			}
		}
		//显示当minutes的值中有-时，
		if (minute.indexOf("-") != -1) {
			$("minutetype3").checked = true;
			$("minute2").disabled = true;
			$("minute4").disabled = true;
			$("minute1").disabled = true;
			var i = minute.indexOf("-");
			var j = minute.substring(0, i);
			var k = minute.substring(i + 1, minute.length);
			$("minuteexpress31").getElementsByTagName("option")[j].selected = true;
			$("minuteexpress32").getElementsByTagName("option")[k].selected = true;
		}
		//显示当minute的值为一个数字或者几个用逗号格开的数字，例如1,2,3,4		
		if (minute.indexOf(",") != -1 || !isNaN(minute)) {
			$("minutetype2").checked = true;
			$("minute3").disabled = true;
			$("minute4").disabled = true;
			$("minute1").disabled = true;
			$("minuteexpress2").value = minute;
		}
		//显示当Day-of-Week的值是?时，
		if (date.indexOf("?") == -1 && day.indexOf("?") != -1) {
			document.all("taskconf.type")[0].checked = true;
			$("date").getElementsByTagName("option")[0].selected = true;
			$("dayselect").style.display = "none";
			$("dateselect").style.display = "";
		}
		//显示当Day-of-month中的值是L时，		
		if (date.indexOf("L") != -1) {
			document.all("taskconf.type")[0].checked = true;
			$("dayselect").style.display = "none";
			$("dateselect").style.display = "";
			$("date").getElementsByTagName("option")[32].selected = true;
		}
		//显示当Day-of-month中的值是1-31时，
		if (date.value != NaN && parseInt(date) >= 1 && parseInt(date) <= 31) {
			document.all("taskconf.type")[0].checked = true;
			$("dayselect").style.display = "none";
			var i = parseInt(date);
			$("date").getElementsByTagName("option")[i].selected = true;
		}
		//显示当Day-of-month中的值是?时，
		if (date.indexOf("?") != -1 && day.indexOf("?") == -1) {
			document.all("taskconf.type")[1].checked = true;
			$("day").getElementsByTagName("option")[0].selected = true;
			$("dateselect").style.display = "none";
			$("dayselect").style.display = "";
		}
		//显示当Day-of-Week中的值是L时，		
		if (day.indexOf("L") != -1) {
			document.all("taskconf.type")[1].checked = true;
			$("dateselect").style.display = "none";
			$("dayselect").style.display = "";
			$("day").getElementsByTagName("option")[7].selected = true;
		}
		//显示当Day-of-Week中的值是1-7时，
		if (!isNaN(day) && parseInt(day) >= 1 && parseInt(day) <= 7) {
			document.all("taskconf.type")[1].checked = true;
			$("dateselect").style.display = "none";
			var i = parseInt(day) - 1;
			$("day").getElementsByTagName("option")[i].selected = true;
		}
	}
}

function Reset(radio)
{
	if (radio.value == "month" && radio.checked) {
		$("dayselect").style.display = "none";
		$("dateselect").style.display = "";
	}
	if (radio.value == "week" && radio.checked) {
		$("dateselect").style.display = "none";
		$("dayselect").style.display = "";
	}
}

function Resethour(radio)
{
	if (radio.value == "hourtype1" && radio.checked) {
		$("hour1").disabled = false;
		$("hour2").disabled = true;
		$("hour3").disabled = true;
		$("hour4").disabled = true;
		$("hourexpress2").value = "";
		$("hourexpress31").getElementsByTagName("option")[0].selected = true;
		$("hourexpress32").getElementsByTagName("option")[0].selected = true;
		$("hourexpress41").getElementsByTagName("option")[0].selected = true;
		$("hourexpress42").value = "";
	}
	if (radio.value == "hourtype2" && radio.checked) {
		$("hour1").disabled = true;
		$("hour2").disabled = false;
		$("hour3").disabled = true;
		$("hour4").disabled = true;
		$("hourexpress31").getElementsByTagName("option")[0].selected = true;
		$("hourexpress32").getElementsByTagName("option")[0].selected = true;
		$("hourexpress41").getElementsByTagName("option")[0].selected = true;
		$("hourexpress42").value = "";
	}
	if (radio.value == "hourtype3" && radio.checked) {
		$("hour1").disabled = true;
		$("hour2").disabled = true;
		$("hour3").disabled = false;
		$("hour4").disabled = true;
		$("hourexpress2").value = "";
		$("hourexpress41").getElementsByTagName("option")[0].selected = true;
		$("hourexpress42").value = "";
	}
	if (radio.value == "hourtype4" && radio.checked) {
		$("hour1").disabled = true;
		$("hour2").disabled = true;
		$("hour3").disabled = true;
		$("hour4").disabled = false;
		$("hourexpress2").value = "";
		$("hourexpress31").getElementsByTagName("option")[0].selected = true;
		$("hourexpress32").getElementsByTagName("option")[0].selected = true;
	}
}

function Resetminute(radio)
{
	if (radio.value == "minutetype1" && radio.checked) {
		$("minute1").disabled = false;
		$("minute2").disabled = true;
		$("minute3").disabled = true;
		$("minute4").disabled = true;
		$("minuteexpress2").value = "";
		$("minuteexpress31").getElementsByTagName("option")[0].selected = true;
		$("minuteexpress32").getElementsByTagName("option")[0].selected = true;
		$("minuteexpress41").getElementsByTagName("option")[0].selected = true;
		$("minuteexpress42").value = "";
	}
	if (radio.value == "minutetype2" && radio.checked) {
		$("minute1").disabled = true;
		$("minute2").disabled = false;
		$("minute3").disabled = true;
		$("minute4").disabled = true;
		$("minuteexpress31").getElementsByTagName("option")[0].selected = true;
		$("minuteexpress32").getElementsByTagName("option")[0].selected = true;
		$("minuteexpress41").getElementsByTagName("option")[0].selected = true;
		$("minuteexpress42").value = "";
	}
	if (radio.value == "minutetype3" && radio.checked) {
		$("minute1").disabled = true;
		$("minute2").disabled = true;
		$("minute3").disabled = false;
		$("minute4").disabled = true;
		$("minuteexpress2").value = "";
		$("minuteexpress41").getElementsByTagName("option")[0].selected = true;
		$("minuteexpress42").value = "";
	}
	if (radio.value == "minutetype4" && radio.checked) {
		$("minute1").disabled = true;
		$("minute2").disabled = true;
		$("minute3").disabled = true;
		$("minute4").disabled = false;
		$("minuteexpress2").value = "";
		$("minuteexpress31").getElementsByTagName("option")[0].selected = true;
		$("minuteexpress32").getElementsByTagName("option")[0].selected = true;
	}
}

//限制输入
function limit(obj, reg)
{
	if (!reg.test(obj.value)) {
		obj.value = obj.value.substring(0, obj.value.length - 1);
		return false;
	}
	return true;
}

function checktime(obj)
{
	while (!limit(obj, /^[0-9,]{0,30}$/));
}

function checktime_hour(obj)
{
	var h = obj.value;
	if (h) {
		if (parseInt(h) > 23) {
			obj.value = h.substring(0, h.length - 1);
			return false;
		}
	}
	while (!limit(obj, /^[0-9,]{0,30}$/));
}

function checktime_mi(obj)
{
	var h = obj.value;
	if (h) {
		if (parseInt(h) > 59) {
			obj.value = h.substring(0, h.length - 1);
			return false;
		}
	}
	while (!limit(obj, /^[0-9,]{0,30}$/));
}

function verifyNumber(obj, symbol, bite)
{
	var bDot = false;
	var bNega = false;
	if (typeof(symbol) == 'undefined') symbol = "";
	if (typeof(bite) == 'undefined') bite = 2;
	bDot = (symbol.indexOf(".") >= 0 ? true : false);
	bNega = (symbol.indexOf("-") >= 0 ? true : false);
	var errInfo = "无效";
	var str = trim(obj.value);
	if (str == "") return true;
	try
	{
		if (!isNumber(str, symbol))
		{
			alert("\"" + str + "\"" + errInfo);
			obj.value = "";
			obj.focus();
			return false;
		}
	}
	catch (e)
	{
		alert("\"" + str + "\"" + errInfo);
		obj.value = "";
		obj.focus();
		return false;
	}
	//验证小数点后两位
	if (str.indexOf(".") >= 0) {
		var temp = str;
		if (str.indexOf("-") >= 0) {
			temp = str.substring(1, str.length);
		}
		if (!behindDot(temp, bite)) {
			alert("填写错误");
			return false;
		}
	}
	return true;
}

function typeNumber(obj, symbol, bite)
{
	var str = trim(obj.value.replace(/\,/g, ""));
	var bDot = false;
	var bNega = false;
	if (typeof(symbol) == 'undefined') symbol = "";
	if (typeof(bite) == 'undefined') bite = 2;
	bDot = (symbol.indexOf(".") >= 0 ? true : false);
	bNega = (symbol.indexOf("-") >= 0 ? true : false);
	if (bNega)  //允许输入负号
	{
		//只能第一位为负号
		if (str.length > 0 && window.event.keyCode == 45)
		{
			window.event.returnValue = false;
			return;
		}

		//只能输入一个负号
		if (str.indexOf("-") >= 0 && window.event.keyCode == 45)
		{
			window.event.returnValue = false;
			return;
		}
	}

	if (bDot)  //允许输入小数点
	{
		//只能输入一个小数点
		if (str.indexOf(".") >= 0 && window.event.keyCode == 46)
		{
			window.event.returnValue = false;
			return;
		}
	}
	//验证小数点后两位
	if (str.indexOf(".") >= 0) {
		var temp = str;
		if (str.indexOf("-") >= 0) {
			temp = str.substring(1, str.length);
		}
		if (! behindDot(temp, bite - 1)) {
			window.event.returnValue = false;
			return;
		}
	}

	if (window.event.keyCode < 48 || window.event.keyCode > 57)
	{
		if ((bDot && window.event.keyCode == 46) || (bNega && window.event.keyCode == 45))
		{
			return;
		}
		window.event.returnValue = false;
	}
}



</script>
</html>