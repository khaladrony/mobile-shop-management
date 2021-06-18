<%-- 
    Document   : left_menu
    Created on : May 22, 2018, 12:53:32 PM
    Author     : sarker
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style type="text/css">
    .nav-list > li > a {
        display: block;
        max-height: 50px;
        line-height: 7px;
        padding-left: 3px;
        text-shadow: none !important;
        font-size: 13px;
        height: auto;
        min-height: 39px;
    }
</style>

<div id="sidebar" class="sidebar responsive ace-save-state">
    <script type="text/javascript">
        try {
            ace.settings.loadState('sidebar');
        } catch (e) {
        }
    </script>

    <div class="sidebar-shortcuts" id="sidebar-shortcuts">
        <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
            <!--<button class="btn btn-success">
                <i class="ace-icon fa fa-signal"></i>
            </button>

            <button class="btn btn-info">
                <i class="ace-icon fa fa-pencil"></i>
            </button>

            <button class="btn btn-warning">
                <i class="ace-icon fa fa-users"></i>
            </button>

            <button class="btn btn-danger">
                <i class="ace-icon fa fa-cogs"></i>
            </button>-->
        </div>

        <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
            <span class="btn btn-success"></span>

            <span class="btn btn-info"></span>

            <span class="btn btn-warning"></span>

            <span class="btn btn-danger"></span>
        </div>
    </div><!-- /.sidebar-shortcuts -->

    <ul class="nav nav-list">
        
        <c:forEach items="${USER.menu.features}" var="module" varStatus="moduleCounter">
            <li class="">
                <a class="dropdown-toggle">
                    <i class="menu-icon fa fa-arrow-circle-o-down"></i>
                    <span class="menu-text">
                        ${fn:escapeXml(module.feature_name)}
                    </span>

                    <b class="arrow fa fa-angle-down"></b>
                </a>

                <b class="arrow"></b>
                
                <ul class="submenu">
                    <c:forEach items="${module.features}" var="fg" varStatus="fgCounter">

                        <li class="">
                            <a class="dropdown-toggle">
                                <i class="menu-icon fa fa-arrow-circle-down"></i>
                                <span class="menu-text">
                                    ${fn:escapeXml(fg.feature_name)}
                                </span>

                                <b class="arrow fa fa-angle-down"></b>
                            </a>

                            <b class="arrow"></b>
                            
                            <ul class="submenu">
                                <c:forEach items="${fg.features}" var="menu" varStatus="menuCounter">

                                    <c:choose>
                                        <c:when test="${menu.is_menu}">
                                            <li class="">
                                                <a 
                                                    class="submenu-custom" 
                                                    onclick="markSelectedMenu('${APP}/${menu.module}/${menu.controller}/${menu.action}#!/${menu.component}');"
                                                    href="${APP}/${menu.module}/${menu.controller}/${menu.action}#!/${menu.component}">
                                                    <i class="menu-icon fa fa-caret-right"></i>
                                                    ${fn:escapeXml(menu.feature_name)}
                                                </a>
                                                <b class="arrow"></b>
                                            </li>
                                        </c:when>
                                    </c:choose>

                                </c:forEach>
                            </ul>
                            
                        </li>

                    </c:forEach>
                </ul>
            </li>
        </c:forEach>
        
    </ul><!-- /.nav-list -->

    <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
        <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
    </div>
</div>
