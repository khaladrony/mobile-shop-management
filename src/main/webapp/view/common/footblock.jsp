
<!--<div class="hr hr-double dotted"></div>-->
<div class="app-footer text-center" id="appFooter">
    <span>Copyright &copy; finQsoft</span>
</div>

<!--<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
    <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
</a>-->



<!-- Footer CSS Style -->
<style>
    #appFooter {
        position: relative;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 30px;
        background-color: #f8f8f8; /* Light gray background */
        text-align: center;
        line-height: 40px; /* Vertically centers the text */
        font-size: 14px;
        color: #333;
//        border-top: 1px solid #ccc;
        z-index: 999;
    }

    /* Adjust scroll-up button position so it doesn't overlap the footer */
    #btn-scroll-up {
        bottom: 70px !important; /* Slightly above the fixed footer */
        position: fixed;
        right: 20px;
        z-index: 1000;
    }
</style>