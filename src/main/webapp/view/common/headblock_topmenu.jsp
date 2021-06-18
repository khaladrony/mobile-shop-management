<%-- 
    Document   : headblock_topmenu
    Created on : 22 May, 2018, 3:41:24 PM
    Author     : sarker
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html lang="en">
    <head>
        <%@include file="resoucelink_head.jsp" %>
    </head>

    <body class="no-skin">
        <div id="navbar" class="navbar navbar-default    navbar-collapse       h-navbar ace-save-state">
            <div class="navbar-container ace-save-state" id="navbar-container">
                <div class="navbar-header pull-left">
                    <a href="${APP}/auth/launcher" class="navbar-brand">
                        <small>
                            <i class="fa fa-server"></i>
                            ${fn:escapeXml(APP_NAME)}
                        </small>
                    </a>

                    <button class="pull-right navbar-toggle navbar-toggle-img collapsed" type="button" data-toggle="collapse" data-target=".navbar-buttons,.navbar-menu">
                        <span class="sr-only">Toggle user menu</span>

                        <img src="${STATIC_RES}/images/avatars/user.jpg" alt="Jason's Photo" />
                    </button>

                    <button class="pull-right navbar-toggle collapsed" type="button" data-toggle="collapse" data-target="#sidebar">
                        <span class="sr-only">Toggle sidebar</span>

                        <span class="icon-bar"></span>

                        <span class="icon-bar"></span>

                        <span class="icon-bar"></span>
                    </button>
                </div>

                <div class="navbar-buttons navbar-header pull-right  collapse navbar-collapse" role="navigation">
                    <ul class="nav ace-nav">
                        <li class="light-blue dropdown-modal">
                            <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                                <img class="nav-user-photo" src="${STATIC_RES}/images/avatars/avatar2.png" alt="${fn:escapeXml(USER.role_name)}" />
                                <span class="user-info">
                                    <small>${fn:escapeXml(USER.last_name)},</small>
                                    ${fn:escapeXml(USER.role_name)}
                                </span>

                                <i class="ace-icon fa fa-caret-down"></i>
                            </a>

                            <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">

                                <li>
                                    <a href="${APP}/user_auth/profile/view">
                                        <i class="ace-icon fa fa-user"></i>
                                        Profile
                                    </a>
                                </li>
                                <li>
                                    <a href="${APP}/user_auth/profile/view#!/change_pwd_view">
                                        <i class="ace-icon fa fa-key"></i>
                                        Change password
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