<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../common/headblock_topmenu.jsp" %>
<script src="${NG_SRC}/inventory/constant.js?v=${SCRIPT_VERSION}"></script>


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
        <div class="main-content-inner" style="background: #FFF;" ng-app="InventoryManagementApp">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <!--<li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a>Home</a>
                    </li>-->
                    <li>
                        <%--<i class="ace-icon fa fa-money home-icon"></i>--%>
                        Inventory
                    </li>
                    <li class="active" id="page_name"></li>
                </ul><!-- /.breadcrumb -->

                <%--<div class="nav-search" id="nav-search" >
                    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">

                        <a class="btn btn-sm btn-primary" ui-sref="{{JCOMPONENT.item_master_add_view}}" ng-if="hasPermission(JMODULE_NAME,  JCOMPONENT.item_master_list_view)">
                            <i class="ace-icon fa fa-user-secret"></i> Item Create
                        </a>

                    </div>
                </div>--%>

                <%--<div class="nav-search" id="nav-search" >
                    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                        
                        <a class="btn btn-sm btn-primary" ui-sref="{{JCOMPONENT.feature_add_view}}" ng-if="hasPermission(JMODULE_NAME,  JCOMPONENT.feature_add_view)">
                            <i class="ace-icon fa fa-plus-circle"></i> Feature
                        </a>
                        <a class="btn btn-sm btn-primary" ui-sref="{{JCOMPONENT.feature_list_view}}" ng-if="hasPermission(JMODULE_NAME, JCOMPONENT.feature_list_view)">
                            <i class="ace-icon fa fa-list"></i> Features
                        </a>
                        
                        <a class="btn btn-sm btn-primary" ui-sref="{{JCOMPONENT.role_add_view}}" ng-if="hasPermission(JMODULE_NAME,  JCOMPONENT.role_add_view)">
                            <i class="ace-icon fa fa-plus-circle"></i> Role
                        </a>
                        <a class="btn btn-sm btn-primary" ui-sref="{{JCOMPONENT.role_list_view}}" ng-if="hasPermission(JMODULE_NAME,  JCOMPONENT.role_list_view)">
                            <i class="ace-icon fa fa-list"></i> Roles
                        </a>
                        <a class="btn btn-sm btn-primary" ui-sref="{{JCOMPONENT.role_feature_view}}" ng-if="hasPermission(JMODULE_NAME,  JCOMPONENT.role_feature_view)">
                            <i class="ace-icon fa fa-gears"></i> Role Feature
                        </a>
                        
                        <a class="btn btn-sm btn-primary" ui-sref="{{JCOMPONENT.user_add_view}}"  ng-if="hasPermission(JMODULE_NAME,  JCOMPONENT.user_add_view)" >
                            <i class="ace-icon fa fa-plus-circle"></i> User
                        </a>
                        <a class="btn btn-sm btn-primary" ui-sref="{{JCOMPONENT.user_list_view}}" ng-if="hasPermission(JMODULE_NAME,  JCOMPONENT.user_list_view)" >
                            <i class="ace-icon fa fa-list"></i> Users
                        </a>
                    </div>

                </div><!-- /.nav-search -->--%>
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
<script src="${NG_SRC}/inventory/app.js?v=${SCRIPT_VERSION}"></script>

<!-- please include the app.service.js & app.directives.js file after each and every app.js file -->
<script src="${ANGULAR}/app.directives.js?v=${SCRIPT_VERSION}"></script>
<script src="${ANGULAR}/app.services.js?v=${SCRIPT_VERSION}"></script>


<!-- include here rest of your ng app controller -->
<script src="${NG_SRC}/inventory/item-master/item_master_form.controller.js?v=${SCRIPT_VERSION}"></script>
<script src="${NG_SRC}/inventory/item-master/item_master_list.controller.js?v=${SCRIPT_VERSION}"></script>
<!-- include here rest of your ng app controller -->

<%@include file="../common/close_page.jsp" %>




