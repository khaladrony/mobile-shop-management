<%-- 
    Document   : headblock_leftmenu
    Created on : 22 May, 2018, 3:24:58 PM
    Author     : sarker
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">
    <head>
        <%@include file="resoucelink_head.jsp" %>
    </head>

    <body class="no-skin">
        <div id="navbar" class="navbar navbar-default          ace-save-state">
            <div class="navbar-container ace-save-state" id="navbar-container">
                <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
                    <span class="sr-only">Toggle sidebar</span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>
                </button>

                <div class="navbar-header pull-left">
                    <a href="${APP}/auth/launcher" class="navbar-brand">
                        <small>
                            <i class="fa fa-balance-scale"></i>
                            ${fn:escapeXml(APP_NAME)}
                        </small>
                    </a>

                    <div class="btn-group">
                        <button data-toggle="dropdown" class="btn dropdown-toggle hospital" style="text-shadow: none; cursor: text; background-color: #6FB3E0 !important; border-color: #6fb3e0 !important;">
                            Logged in as <b>${fn:escapeXml(USER.role_name)}</b>
                        </button>
                    </div>
                </div>

                <div class="navbar-buttons navbar-header pull-right" role="navigation">
                    <ul class="nav ace-nav">

                        <li class="blue">
                            <a style="cursor: pointer;" target="_blank" href="${APP}/auth/version">

                                <span class="badge badge-info">Version: ${APP_VERSION}</span>
                            </a>
                        </li>

                        <li class="blue">
                            <a style="cursor: pointer;" href="${APP}/core/data_sync">
                                <i class="ace-icon fa fa-cloud-download"></i>
                                <span class="badge badge-info"></span>
                            </a>
                        </li>

                        <li class="light-blue dropdown-modal">
                            <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                                <img class="nav-user-photo" src="${STATIC_RES}/images/avatars/avatar2.png" alt="${fn:escapeXml(USER.role_name)}" />
                                <span class="user-info">
                                    <small>${fn:escapeXml(USER.full_name)},</small>
                                    ${fn:escapeXml(USER.role_name)}
                                </span>

                                <i class="ace-icon fa fa-caret-down"></i>
                            </a>

                            <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">

                                <li>
                                    <a href="${APP}/user_auth_management/profile/view">
                                        <i class="ace-icon fa fa-user"></i>
                                        Profile
                                    </a>
                                </li>

                                <li class="divider"></li>

                                <li>
                                    <a href="${APP}/auth/logout">
                                        <i class="ace-icon fa fa-power-off"></i>
                                        Logout
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div><!-- /.navbar-container -->
        </div>

