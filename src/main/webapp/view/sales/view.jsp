<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../common/headblock_topmenu.jsp" %>
<script src="${NG_SRC}/sales/constant.js?v=${SCRIPT_VERSION}"></script>
<link rel="stylesheet" href="${STATIC_RES}/css/custom-pos.css?v=${SCRIPT_VERSION}" />


<div class="main-container ace-save-state" id="main-container">
    <script type="text/javascript">
        try{
            ace.settings.loadState('main-container');
        } catch (e) {
        }
    </script>

    <%-- <%@include file="../common/top_menu.jsp" %>
    <%@include file="../common/left_menu.jsp" %>
    <%@ include file="left_menu.jsp" %> --%>

    <div class="main-content">
        <div class="main-content-inner" style="background: #FFF;" ng-app="PosApp">

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

<%-- <%@include file="../common/footblock.jsp" %> --%>
</div><!-- /.main-container -->
<%@include file="../common/resoucelink_footer.jsp" %>

<!-- angularJS script -->
<script src="${NG_SRC}/sales/app.js?v=${SCRIPT_VERSION}"></script>

<!-- please include the app.service.js & app.directives.js file after each and every app.js file -->
<script src="${ANGULAR}/app.directives.js?v=${SCRIPT_VERSION}"></script>
<script src="${ANGULAR}/app.services.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/sales/pos.directives.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/sales/service/customer.service.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/sales/service/payment.service.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/sales/service/order.service.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/sales/service/return.service.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/sales/service/transaction.service.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/sales/pos-report/report.service.js?v=${SCRIPT_VERSION}"></script>


<!-- include here rest of your ng app controller -->
<script src="${NG_SRC}/sales/pos/pos_form.controller.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/sales/pos-dashboard/pos_dashboard_form.controller.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/sales/pos-report/sales_report.form.controller.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/sales/pos-transaction/transaction_form.controller.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/sales/pos-report/pos_receipt.form.controller.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/sales/pos-report/invoice.form.controller.js?v=${SCRIPT_VERSION}"></script>

<!-- include here rest of your ng app controller -->

<%@include file="../common/close_page.jsp" %>
