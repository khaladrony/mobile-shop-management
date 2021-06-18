<%-- 
    Document   : login_page
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta charset="utf-8" />
        <link rel="icon" type="image/x-icon" href="${STATIC_RES}/images/favicon.ico" />
        <title>Login Page - ${fn:escapeXml(APP_NAME)}</title>
        <% response.addHeader("x-frame-options","DENY"); %>

        <meta name="description" content="User login page" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

        <!-- bootstrap & fontawesome -->
        <link rel="stylesheet" href="${STATIC_RES}/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${STATIC_RES}/font-awesome/4.5.0/css/font-awesome.min.css" />
        
        <link rel="stylesheet" href="${STATIC_RES}/css/custom.css?v=${SCRIPT_VERSION}" />
        
        <!-- text fonts -->
        <link rel="stylesheet" href="${STATIC_RES}/css/fonts.googleapis.com.css" />

        <!-- ace styles -->
        <link rel="stylesheet" href="${STATIC_RES}/css/ace.min.css" />

        <!--[if lte IE 9]>
                <link rel="stylesheet" href="assets/css/ace-part2.min.css" />
        <![endif]-->
        <link rel="stylesheet" href="${STATIC_RES}/css/ace-rtl.min.css" />

        <!--[if lte IE 9]>
          <link rel="stylesheet" href="${STATIC_RES}/css/ace-ie.min.css" />
        <![endif]-->

        <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

        <!--[if lte IE 8]>
        <script src="${STATIC_RES}/js/html5shiv.min.js"></script>
        <script src="${STATIC_RES}/js/respond.min.js"></script>
        <![endif]-->
    </head>

    <body class="login-layout light-login" style="background: #C1CDCD">
        <div class="main-container">
            <div class="main-content">
                <div class="row">
                    <div class="col-sm-10 col-sm-offset-1">
                        <div class="login-container">
                            <div class="center">
                                <h1>
                                    <i class="ace-icon fa fa-server primary-color"></i>
                                    <span class="primary-color">${fn:escapeXml(APP_NAME)}</span>
                                    <span class="grey" id="id-text2"></span>
                                </h1>
                                <h4>${fn:escapeXml(APP_FULL_NAME)}</h4>
                            </div>

                            <div class="space-6"></div>

                            <div class="position-relative">
                                <div id="login-box" class="login-box visible widget-box no-border">
                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <h4 class="header blue lighter bigger">
                                                <i class="ace-icon fa fa-key green"></i>
                                                Please Enter Your Credentials
                                            </h4>

                                            <div class="space-6"></div>

                                            <form method="POST" id="usrLoginForm" action="${APP}/auth/login" autocomplete="off">
                                                <fieldset>
                                                    <%--<label class="block clearfix">
                                                        <span class="block input-icon input-icon-right">
                                                            <input type="text" class="form-control" name="usremail" id="usremail" placeholder="Username/Email" />
                                                            <i class="ace-icon fa fa-user"></i>
                                                        </span>
                                                    </label>--%>

                                                    <label class="block clearfix">
                                                        <span class="block input-icon input-icon-right">
                                                            <input type="text" class="form-control" name="lanId" id="lanId" placeholder="User Id" />
                                                            <i class="ace-icon fa fa-user"></i>
                                                        </span>
                                                    </label>

                                                    <label class="block clearfix">
                                                        <span class="block input-icon input-icon-right">
                                                            <input type="password" class="form-control" id="usrpkeytxt" placeholder="Password" />
                                                            <i class="ace-icon fa fa-lock"></i>
                                                        </span>
                                                    </label>

                                                    <input type="hidden" class="form-control" id="usrpkeycnv" name="usrpkeycnv" />
                                                    <div class="space"></div>

                                                    <div class="clearfix">
                                                        <label class="text text-danger">${status}</label>
                                                    </div>

                                                    <div class="clearfix">
                                                        <label class="inline">
                                                            <!--<div class="ftoolbar">
                                                                <a href="#" data-target="#forgot-box" class="forgot-password-link">
                                                                    <i class="ace-icon fa fa-unlock"></i>
                                                                    I forgot my password
                                                                </a>
                                                            </div>-->
                                                        </label>

                                                        <button type="submit" id="loginButton" class="width-35 pull-right btn btn-sm btn-primary">
                                                            <i class="ace-icon fa fa-key"></i>
                                                            <span class="bigger-110">Login</span>
                                                        </button>
                                                    </div>

                                                    <div class="space-4"></div>
                                                </fieldset>
                                            </form>

                                        </div><!-- /.widget-main -->
                                        <div class="toolbar clearfix login-footer">
                                            <div>
                                                &copy; finQsoft
                                            </div>
                                            <div>
                                                Version: ${APP_VERSION}&nbsp;
                                            </div>
                                        </div>

                                        <!--<div class="toolbar clearfix">
                                            <div>
                                                <a href="#" data-target="#forgot-box" class="forgot-password-link">
                                                    <i class="ace-icon fa fa-arrow-left"></i>
                                                    I forgot my password
                                                </a>
                                            </div>

                                            <div>
                                                <a href="#" data-target="#signup-box" class="user-signup-link">
                                                    I want to register
                                                    <i class="ace-icon fa fa-arrow-right"></i>
                                                </a>
                                            </div>
                                        </div>-->
                                    </div><!-- /.widget-body -->
                                </div><!-- /.login-box -->
                                
                                <div id="forgot-box" class="forgot-box widget-box no-border">
                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <h4 class="header blue lighter bigger">
                                                <i class="ace-icon fa fa-key"></i>
                                                Retrieve Password
                                            </h4>

                                            <div class="space-6"></div>
                                            <p>
                                                Enter your email and to receive instructions
                                            </p>

                                            <!-- form method="" action=""  autocomplete="off" >
                                                <fieldset>
                                                    <label class="block clearfix">
                                                        <span class="block input-icon input-icon-right">
                                                            <input type="email" class="form-control" placeholder="Email" />
                                                            <i class="ace-icon fa fa-envelope"></i>
                                                        </span>
                                                    </label>

                                                    <div class="clearfix">
                                                        <button type="submit" class="width-35 pull-right btn btn-sm btn-warning">
                                                            <i class="ace-icon fa fa-send"></i>
                                                            <span class="bigger-110">Send Me!</span>
                                                        </button>
                                                    </div>
                                                </fieldset>
                                            </form -->
                                        </div><!-- /.widget-main -->

                                        <div class="toolbar center">
                                            <a href="#" data-target="#login-box" class="back-to-login-link">
                                                Back to login
                                                <i class="ace-icon fa fa-arrow-right"></i>
                                            </a>
                                        </div>
                                    </div><!-- /.widget-body -->
                                </div><!-- /.forgot-box -->

                            </div><!-- /.position-relative -->

                        </div>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.main-content -->
        </div><!-- /.main-container -->

        <!-- basic scripts -->

        <!--[if !IE]> -->
        <script src="${STATIC_RES}/js/jquery-2.1.4.min.js"></script>
        <script src="${fn:escapeXml(STATIC_RES)}/js/AesUtil.js"></script>
        <script src="${fn:escapeXml(STATIC_RES)}/js/aes.js"></script>
        <script src="${fn:escapeXml(STATIC_RES)}/js/pbkdf2.js"></script>
        <script type="text/javascript">var AE5_SKY = "sKey43Sx5@cRetk4";</script>


        <!-- <![endif]-->

        <!--[if IE]>
        <script src="${STATIC_RES}/js/jquery-1.11.3.min.js"></script>
        <![endif]-->
        <script type="text/javascript">
            if ('ontouchstart' in document.documentElement)
                document.write("<script src='${STATIC_RES}/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
        </script>

        <!-- inline scripts related to this page -->
        <script type="text/javascript">
            jQuery(function ($) {
                $(document).on('click', '.toolbar a[data-target], .ftoolbar a[data-target]', function (e) {
                    e.preventDefault();
                    var target = $(this).data('target');
                    $('.widget-box.visible').removeClass('visible');//hide others
                    $(target).addClass('visible');//show target
                });
                
                $("body").hover(function() {
                    var value = $("input#usrpkeytxt").val();
                    if( value.trim().length > 0 ){
                        $("#usrpkeycnv").val(AES_ENC(value, AE5_SKY));
                    } else{
                        $("#usrpkeycnv").val("");
                    }
                });
                
                $("button#loginButton").mouseenter(function(){
                    var value = $("input#usrpkeytxt").val();
                    if( value.trim().length > 0 ){
                        $("#usrpkeycnv").val(AES_ENC(value, AE5_SKY));
                    } else{
                        $("#usrpkeycnv").val("");
                    }
                    
                });
                
                $("form#usrLoginForm").submit(function(e){
                    $("form#usrLoginForm *").filter(":input").each(function(){
                        if ($(this).attr("id") === "usrpkeytxt"){
                            $(this).val("");
                        }
                    });
                });
                
                $("input#lanId").keyup(function() {
                    var value = $("input#usrpkeytxt").val();
                    if( value.trim().length > 0 ){
                        $("#usrpkeycnv").val(AES_ENC(value, AE5_SKY));
                    } else{
                        $("#usrpkeycnv").val("");
                    }
                }).keyup();
                
                $("#usrpkeytxt").keyup(function () {
                    var value = $(this).val();
                    if( value.trim().length > 0 ){
                        $("#usrpkeycnv").val(AES_ENC(value, AE5_SKY));
                    } else{
                        $("#usrpkeycnv").val("");
                    }
                }).keyup();

            });



            //you don't need this, just used for changing background
            jQuery(function ($) {
                $('#btn-login-dark').on('click', function (e) {
                    $('body').attr('class', 'login-layout');
                    $('#id-text2').attr('class', 'white');
                    $('#id-company-text').attr('class', 'blue');

                    e.preventDefault();
                });
                $('#btn-login-light').on('click', function (e) {
                    $('body').attr('class', 'login-layout light-login');
                    $('#id-text2').attr('class', 'grey');
                    $('#id-company-text').attr('class', 'blue');

                    e.preventDefault();
                });
                $('#btn-login-blur').on('click', function (e) {
                    $('body').attr('class', 'login-layout blur-login');
                    $('#id-text2').attr('class', 'white');
                    $('#id-company-text').attr('class', 'light-blue');

                    e.preventDefault();
                });

            });
            
            function AES_ENC(pText, unqKey){
                var iv = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
                var salt = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);

                var aesUtil = new AesUtil(128, 1000);
                var ciphertext = aesUtil.encrypt(salt, iv, unqKey, pText);

                var aesPassword = (iv + "::" + salt + "::" + ciphertext);
                return btoa(aesPassword);
            }
            
        </script>
    </body>
</html>
