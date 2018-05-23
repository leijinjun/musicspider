<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
<link rel="stylesheet" href="${root}/bootstrap/css/bootstrap.min.css">
 <style rel="stylesheet">
     .login-content{
         margin-top: 200px;
     }
     body{
         background-color: #0f0f0f;
     }
    .login-form .btn{
        height: 45px;
        margin: 0;
        padding: 0 20px;
        vertical-align: middle;
        background: #de615e;
        border: 0;
        font-family: 'Roboto', sans-serif;
        font-size: 16px;
        font-weight: 300;
        line-height: 45px;
        color: #fff;
        border-radius: 4px;
        text-shadow: none;
        box-shadow: none;
        transition: all .3s;
        display:inline-block;
        width:100%;
    }
     .form-top{
         color: #FFF;
         text-align: center;
     }
     .login-form input{
         height:45px;
     }
 </style>
</head>
<body>
<div class="login-content">
    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>Login</h3>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-lock"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <form role="form" action="/api/auth/login" method="post" class="login-form">
                            <div class="form-group">
                                <label class="sr-only" for="username">Username</label>
                                <input type="text" name="username" placeholder="Username..." class="form-username form-control" id="form-username">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="password">Password</label>
                                <input type="password" name="password" placeholder="Password..." class="form-password form-control" id="form-password">
                            </div>
                            <button type="submit" class="btn">login</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
<script type="text/javascript" src="${root}/js/jquery.min.js"></script>
<script type="text/javascript" src="${root}/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">

</script>
</html>