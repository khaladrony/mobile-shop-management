<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../common/headblock_topmenu.jsp" %>
<script src="${NG_SRC}/application_common/constant.js?v=${SCRIPT_VERSION}"></script>


<div class="main-container ace-save-state" id="main-container">
    <script type="text/javascript">
        try{
            ace.settings.loadState('main-container');
        } catch (e) {
        }
    </script>

    <%@include file="../common/top_menu.jsp" %>
    <%--<%@include file="../common/left_menu.jsp" %>--%>

    <div class="main-content">
        <div class="main-content-inner" style="background: #FFF;" ng-app="ApplicationCommonApp">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <!--<li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a>Home</a>
                    </li>-->
                    <li>
                        <%--<i class="ace-icon fa fa-money home-icon"></i>--%>
                        General Settings
                    </li>
                    <li class="active" id="page_name"></li>
                </ul><!-- /.breadcrumb -->
            </div>

            <div class="page-content">

                <div id="operation_scs_alert" class="hide alert alert-success">
                    <p id="operation_scs_msg">this alert dialog</p>
                </div>

                <div id="operation_dng_alert" class="hide alert alert-danger">
                    <p id="operation_dng_msg">this alert dialog</p>
                </div>

                <ui-view></ui-view>

            </div><!-- /.page-content -->

        </div>
    </div><!-- /.main-content -->

    <%@include file="../common/footblock.jsp" %>
</div><!-- /.main-container -->
<%@include file="../common/resoucelink_footer.jsp" %>

<!-- angularJS script -->
<script src="${NG_SRC}/application_common/app.js?v=${SCRIPT_VERSION}"></script>

<!-- please include the app.service.js & app.directives.js file after each and every app.js file -->
<script src="${ANGULAR}/app.directives.js?v=${SCRIPT_VERSION}"></script>
<script src="${ANGULAR}/app.services.js?v=${SCRIPT_VERSION}"></script>


<!-- include here rest of your ng app controller -->
<script src="${NG_SRC}/application_common/bank_info/bank_info_form.controller.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/application_common/bank_info/bank_info_list.controller.js?v=${SCRIPT_VERSION}"></script>

<script src="${NG_SRC}/application_common/supplier_info/supplier_info_form.controller.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/application_common/supplier_info/supplier_info_list.controller.js?v=${SCRIPT_VERSION}"></script>

<script src="${NG_SRC}/application_common/customer_info/customer_info_form.controller.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/application_common/customer_info/customer_info_list.controller.js?v=${SCRIPT_VERSION}"></script>

<script src="${NG_SRC}/application_common/employee_info/employee_info_form.controller.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/application_common/employee_info/employee_info_list.controller.js?v=${SCRIPT_VERSION}"></script>

<%@include file="../common/close_page.jsp" %>




