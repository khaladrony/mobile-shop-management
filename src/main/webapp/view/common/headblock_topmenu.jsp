<%-- 
    Document   : headblock_topmenu
    Created on : 22 May, 2018, 3:41:24 PM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html lang="en">
    <head>
        <%@include file="resoucelink_head.jsp" %>

        <style>
            .top-bar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 8px 20px;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                font-size: 16px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            .organization-name {
                font-weight: 700;
                font-size: 18px;
            }
            .current-date {
                font-style: italic;
                font-size: 15px;
                opacity: 0.85;
            }
            @media (max-width: 600px) {
                .top-bar {
                    flex-direction: column;
                    text-align: center;
                }
                .organization-name,
                .current-date {
                    font-size: 14px;
                }
            }
            .top-bar span {
                margin: 0 10px;
            }

            .separator {
                margin: 0 8px;
                font-weight: bold;
            }
        </style>
    </head>

    <body class="no-skin">
        <div id="navbar" class="navbar navbar-default    navbar-collapse       h-navbar ace-save-state">
            <div class="navbar-container ace-save-state" id="navbar-container">
                <div class="navbar-header pull-left">
                    <a href="${APP}/auth/launcher" class="navbar-brand">
                        <small>
                            <div class="top-bar">
                                <c:if test="${not empty ORGANIZATION_NAME}">
                                    <div class="organization-name">
                                        ${fn:escapeXml(ORGANIZATION_NAME)}
                                    </div>
                                    <span class="separator">|</span>
                                </c:if>
                                <div class="current-date">${fn:escapeXml(CURRENT_DATE)}</div>
                            </div>
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
                                <div class="user-info" style="display:inline-block; margin-right: 10px;">
                                    <strong>${fn:escapeXml(USER.last_name)}</strong>
                                    <small class="text-muted">(${fn:escapeXml(USER.role_name)})</small>
                                </div>

                                <c:if test="${not empty fn:trim(BRANCH_CODE)}">
                                    <span class="badge badge-info" style="margin-left: 8px;">
                                        <i class="fa fa-code-fork"></i> ${fn:escapeXml(BRANCH_CODE)}
                                    </span>
                                </c:if>

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