<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="UTF-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
                    <meta name="description" content="User login page" />

                    <title>Login - ${fn:escapeXml(APP_NAME)}</title>

                    <!-- Styles -->
                    <link rel="stylesheet" href="${STATIC_RES}/css/bootstrap.min.css" />
                    <link rel="stylesheet" href="${STATIC_RES}/font-awesome/4.5.0/css/font-awesome.min.css" />
                    <link rel="stylesheet" href="${STATIC_RES}/css/custom.css?v=${SCRIPT_VERSION}" />
                    <link rel="stylesheet" href="${STATIC_RES}/css/custom-login.css?v=${SCRIPT_VERSION}" />
                    <link rel="stylesheet" href="${STATIC_RES}/css/fonts.googleapis.com.css" />
                    <link rel="stylesheet" href="${STATIC_RES}/css/ace.min.css" />
                    <link rel="stylesheet" href="${STATIC_RES}/css/ace-rtl.min.css" />

                </head>

                <body class="login-layout light-login" style="background: #434D55">

                    <div class="container" style="padding-top: 100px;">
                        <div class="login-box shadow p-4 rounded bg-white mx-auto" style="max-width: 400px;">
                            <div class="text-center mb-4">
                                <i class="fa fa-user fa-2x text-primary"></i>
                            </div>
                            <h4 class="text-center mb-3" style="color: black;">Welcome Back!</h4>
                            <p class="text-center mb-4">Please enter your credentials to log in.</p>

                            <form method="POST" action="${APP}/auth/login" id="usrLoginForm" autocomplete="off" style="padding-left: 40px; padding-right: 40px;">

                                <div class="form-group input-icon">
                                    <i class="fa fa-user"></i>
                                    <input type="text" class="form-control" name="lanId" id="lanId"
                                        placeholder="username" autocomplete="username" />
                                </div>

                                <div class="form-group input-icon">
                                    <i class="fa fa-lock"></i>
                                    <input type="password" class="form-control" name="password" placeholder="********"
                                        autocomplete="current-password" />
                                </div>

                                <input type="hidden" id="usrpkeytxt" />
                                <input type="hidden" id="usrpkeycnv" name="usrpkeycnv" />

                                <button type="submit" id="loginButton" class="btn btn-primary btn-block">
                                    <i class="fa fa-key"></i> Login
                                </button>
                            </form>

                            <div id="alerts-container" style="position: fixed; top: 20px; right: 20px; z-index: 1055; width: 300px;">
                                <c:if test="${param.error != null}">
                                    <div class="alert alert-danger alert-dismissible fade in shadow-sm rounded" role="alert">
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">&times;</button>
                                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                        Invalid username or password
                                    </div>
                                </c:if>

                                <c:if test="${param.logout != null}">
                                    <div class="alert alert-success alert-dismissible fade in shadow-sm rounded" role="alert">
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">&times;</button>
                                        <span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>
                                        You have been logged out
                                    </div>
                                </c:if>

                                <c:if test="${param.errorMessage != null}">
                                    <div class="alert alert-danger alert-dismissible fade in shadow-sm rounded" role="alert">
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">&times;</button>
                                        <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
                                        ${param.errorMessage}
                                    </div>
                                </c:if>
                            </div>


                            <div class="text-center text-muted mt-4">
                                &copy; finQsoft | Version: ${APP_VERSION}
                            </div>
                        </div>
                    </div>

                    <!-- Scripts -->
                    <script src="${STATIC_RES}/js/jquery-2.1.4.min.js"></script>
                    <script src="${STATIC_RES}/js/AesUtil.js"></script>
                    <script src="${STATIC_RES}/js/aes.js"></script>
                    <script src="${STATIC_RES}/js/pbkdf2.js"></script>
                    <script>
                        var AE5_SKY = "sKey43Sx5@cRetk4";
                    </script>

                    <script type="text/javascript">
                        function AES_ENC(pText, unqKey) {
                            var iv = CryptoJS.lib.WordArray.random(128 / 8).toString(CryptoJS.enc.Hex);
                            var salt = CryptoJS.lib.WordArray.random(128 / 8).toString(CryptoJS.enc.Hex);
                            var aesUtil = new AesUtil(128, 1000);
                            var ciphertext = aesUtil.encrypt(salt, iv, unqKey, pText);
                            return btoa(iv + "::" + salt + "::" + ciphertext);
                        }

                        function updateEncryptedValue() {
                            const passwordValue = $("input[name='password']").val();
                            $("#usrpkeytxt").val(passwordValue);

                            const $keyInput = $("#usrpkeytxt");
                            const value = $keyInput.val();
                            if (value && value.trim().length > 0) {
                                $("#usrpkeycnv").val(AES_ENC(value, AE5_SKY));
                            } else {
                                $("#usrpkeycnv").val("");
                            }
                        }

                        $(function () {
                            // Keep encryption updated on user input
                            $("#lanId, #usrpkeytxt").on("keyup", updateEncryptedValue);
                            $("body").on("mouseenter", updateEncryptedValue);
                            $("#loginButton").on("mouseenter", updateEncryptedValue);

                            // Final encryption before submit
                            $("#usrLoginForm").on("submit", function () {
                                updateEncryptedValue(); // ensure latest value is set
                                $("#usrpkeytxt").val(""); // clear sensitive hidden input
                            });
                        });

                        setTimeout(function() {
                            $("#alerts-container .alert").fadeTo(500, 0).slideUp(500, function(){
                                $(this).remove();
                            });
                        }, 5000);

                    </script>

                </body>

                </html>