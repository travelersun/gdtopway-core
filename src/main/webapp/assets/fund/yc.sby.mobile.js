$.fn.extend({weuiSelectPicker:function(a){a=$.extend({title:undefined,textAlign:"center",},a);$(this).hide();$(this).each(function(){var c=$(this);c.nextAll(".weui-selectPicker").remove();var b={name:{},values:[]};c.find("option").each(function(d){if($(this).val()){b.name[$(this).text()]=d;b.values.push($(this).text())}});c.after($("<input>").attr({"class":c.attr("class")+" weui-selectPicker",type:"text",value:c.find("option:selected").text(),placeholder:c.attr("placeholder")}).picker({title:$(this).data("title")||a.title||"",cols:[{textAlign:a.textAlign,values:b.values}],onChange:function(d,e){c[0].selectedIndex=b.name[e[0]];c.valid()},onOpen:function(){$("input").blur();c.focus()}}))});return $(this)},appCompressionUploadFile:function(a){a=$.extend({url:undefined,postName:"fileData",maxWidth:undefined,maxHeight:undefined,onBeforeSubmit:undefined,onSuccess:undefined,onError:undefined,onComplete:undefined,},a);$(this).change(function(){var g=$(this);var f=event.target.files[0];if(f.type.indexOf("image")==0){var b=new FileReader(),c=new Image();var d=document.createElement("canvas");var e=d.getContext("2d");c.onload=function(){var i=this.width;var m=this.height;var l=a.maxWidth||550,k=a.maxHeight||550;var j=i,h=m;if(i>l||m>k){if(i/m>l/k){j=l;h=Math.round(l*(m/i))}else{h=k;j=Math.round(k*(i/m))}}d.width=j;d.height=h;e.clearRect(0,0,j,h);e.drawImage(c,0,0,j,h);d.toBlob(function(n){a.onBeforeSubmit&&a.onBeforeSubmit(n,g);var p=new XMLHttpRequest();p.onreadystatechange=function(){switch(p.readyState){case 4:var q=JSON.parse(p.responseText);if(p.status=="200"){a.onSuccess&&a.onSuccess(q,g)}else{a.onError&&a.onError(q,g)}a.onComplete&&a.onComplete(q,p.status=="200"?"success":"error",g);break}};p.open("POST",a.url,true);var o=new FormData();o.append(a.postName,n);p.send(o)},f.type||"image/png")};b.onload=function(h){c.src=h.target.result};b.readAsDataURL(f)}})}});var wechatJSAPIList;$(function(){if(wechatJSAPIList&&wechatJSAPIList.length){var a=WechatParameters.base64Decrypt().split("|");wx.config({debug:Environment===SystemEnvironment.name.DEV,appId:a[0],timestamp:a[1],nonceStr:a[2],signature:a[3],jsApiList:wechatJSAPIList});wx.error(function(b){console.log("wx config error: "+JSON.stringify(b))})}$('select[data-toggle="weuiSelect"]').weuiSelectPicker();FastClick.attach(document.body)});