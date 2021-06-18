<%-- 
    Document   : resoucelink_head
    Created on : May 22, 2018, 11:21:32 AM
    Author     : sarker
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- basic scripts -->

<!--[if !IE]> -->
<script src="${fn:escapeXml(STATIC_RES)}/js/jquery-2.1.4.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="${fn:escapeXml(STATIC_RES)}/js/jquery-1.11.3.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<![endif]-->
<script type="text/javascript">
    var _shskr_ = "${XATKN}";
    if ('ontouchstart' in document.documentElement)
        document.write("<script src='${fn:escapeXml(STATIC_RES)}/js/jquery.mobile.custom.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}'>" + "<" + "/script>");
</script>
<script src="${fn:escapeXml(STATIC_RES)}/js/bootstrap.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/js/moment.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/js/bootstrap-datetimepicker.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
  <script src="${fn:escapeXml(STATIC_RES)}/js/excanvas.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<![endif]-->
<script src="${fn:escapeXml(STATIC_RES)}/js/jquery-ui.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/js/jquery.ui.touch-punch.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/js/AesUtil.js"></script>
<script src="${fn:escapeXml(STATIC_RES)}/js/aes.js"></script>
<script src="${fn:escapeXml(STATIC_RES)}/js/pbkdf2.js"></script>                                                                                                                                                                                                <script type="text/javascript">var AE5_SKY = "sKey43Sx5@cRetk4";</script>

<!-- ace scripts -->
<script src="${fn:escapeXml(STATIC_RES)}/js/ace-elements.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/js/ace.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/js/select2.full.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
    jQuery(function ($) { 
        $(document).find('.date-picker').datepicker({
            autoclose: true,
            todayHighlight: true,
            dateFormat: 'yy-mm-dd'
        });
        
        /*if( _SHOW_ALERT_ == "1" && _FORCE_CHANGE_ == "0"){
            angular.element('#pawrd_expiry_alert').removeClass('hide');
        }*/
        
        if( _FORCE_CHANGE_DEFAULT_PASSWORD_ == "1" ){
            var url = _baseurl_ + "user_auth/profile/view#!/change_pwd_view";
            window.location.replace(url);
        }
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

<!-- AngularJS 1.6.10 -->
<script src="${fn:escapeXml(STATIC_RES)}/angular/angular.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/angular/ui-bootstrap-tpls-0.10.0.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>

<script src="${fn:escapeXml(STATIC_RES)}/angular/angular-ui-router.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/angular/angular-animate.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/angular/angular-aria.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/angular/angular-messages.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/angular/angular-sanitize.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>

<script src="${fn:escapeXml(STATIC_RES)}/angular/angular-material.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/angular/angular-datepicker.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/angular/angular-select2.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/angular/ngDialog.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/angular/ngToast.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>

<script src="${fn:escapeXml(STATIC_RES)}/angular/angular-growl.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/angular/angular-growl.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>

<script src="${fn:escapeXml(STATIC_RES)}/js/jquery.jqGrid.min.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/js/grid.locale-en.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>

<!--
<script src="${fn:escapeXml(STATIC_RES)}/angular/app.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
<script src="${fn:escapeXml(STATIC_RES)}/angular/app.services.js?v=${fn:escapeXml(SCRIPT_VERSION)}"></script>
-->