<style>
 .loading-container {
   z-index:2000;
   position:fixed;
   top:0;
   left:0;
   right:0;
   bottom:0;
   background:rgba(0,0,0,.55)
 }
 .loading-container.loading-inactive {
   display:none
 }
 .loading-container.loading-inactive .loading-progress {
   display:none
 }
 .loading-container .loading-progress {
   z-index:2000;
   position:fixed;
   height:10px;
   width:10px;
   margin:auto;
   top:0;
   left:0;
   right:0;
   bottom:0
 }
 .loading-container .loading-progress .rotator {
   -webkit-animation:spin 30s infinite linear;
   -moz-animation:spin 30s infinite linear;
   -o-animation:spin 30s infinite linear;
   animation:spin 30s infinite linear
 }
 .loading-container .loading-progress>.rotator {
   left:-100px
 }
 .loading-container .rotator {
   -webkit-background-origin:border-box;
   -moz-background-origin:border-box;
   background-origin:border-box;
   background-color:#fff;
   width:200px;
   height:7px;
   opacity:.75;
   position:absolute;
   top:0;
   left:0
 }
 .loading-container .colored {
   background-color:#2dc3e8!important
 }
  input[type=checkbox]+.text:before,input[type=radio]+.text:before {
   font-family:fontAwesome;
   font-weight:bold;
   font-size:13px;
   color:#333;
   content:" ";
   background-color:#fafafa;
   border:1px solid #c8c8c8;
   box-shadow:0 1px 2px rgba(0,0,0,.05);
   border-radius:0;
   display:inline-block;
   text-align:center;
   vertical-align:middle;
   height:18px;
   line-height:16px;
   min-width:18px;
   margin-right:5px;
   margin-bottom:2px;
   -webkit-transition:all .3s ease;
   -moz-transition:all .3s ease;
   -o-transition:all .3s ease;
   transition:all .3s ease
 }
 .navbar .navbar-inner .navbar-header .navbar-account .setting-container input[type=checkbox]:checked+.text:before {
   border-color:#2dc3e8;
   color:#fb6e52
 }
 input[type=checkbox]+.text:hover:before,input[type=radio]+.text:hover:before {
   border-color:#737373
 }
 input[type=checkbox]:checked+.text:before,input[type=radio]:checked+.text:before {
   display:inline-block;
   content:'';
   background-color:#f5f8fc;
   -webkit-box-shadow:0 1px 2px rgba(0,0,0,.05),inset 0 -15px 10px -12px rgba(0,0,0,.05),inset 15px 10px -12px rgba(255,255,255,.1);
   -moz-box-shadow:0 1px 2px rgba(0,0,0,.05),inset 0 -15px 10px -12px rgba(0,0,0,.05),inset 15px 10px -12px rgba(255,255,255,.1);
   box-shadow:0 1px 2px rgba(0,0,0,.05),inset 0 -15px 10px -12px rgba(0,0,0,.05),inset 15px 10px -12px rgba(255,255,255,.1);
   border-color:#333
 }
 input[type=checkbox]+.text:before,input[type=radio]+.text:before {
   font-family:fontAwesome;
   font-weight:bold;
   font-size:13px;
   color:#333;
   content:" ";
   background-color:#fafafa;
   border:1px solid #c8c8c8;
   box-shadow:0 1px 2px rgba(0,0,0,.05);
   border-radius:0;
   display:inline-block;
   text-align:center;
   vertical-align:middle;
   height:18px;
   line-height:16px;
   min-width:18px;
   margin-right:5px;
   margin-bottom:2px;
   -webkit-transition:all .3s ease;
   -moz-transition:all .3s ease;
   -o-transition:all .3s ease;
   transition:all .3s ease
 }
 input[type=checkbox]:checked,input[type=radio]:checked,input[type=checkbox]:focus,input[type=radio]:focus {
   outline:none!important
 }

 input[type=checkbox],input[type=radio] {
/*    opacity:0; */
/*    position:absolute; */
/*    left:-9999px; */
/*    z-index:12; */
/*    width:18px; */
/*    height:18px; */
/*    cursor:pointer */
 }

label {
     font-weight: normal;
     font-size: 13px;
     vertical-align: middle;
 }
</style>

<div class="loading-container" >
    <div class="loading-progress">
        <div class="rotator">
            <div class="rotator">
                <div class="rotator colored">
                    <div class="rotator">
                        <div class="rotator colored">
                            <div class="rotator colored"></div>
                            <div class="rotator"></div>
                        </div>
                        <div class="rotator colored"></div>
                    </div>
                    <div class="rotator"></div>
                </div>
                <div class="rotator"></div>
            </div>
            <div class="rotator"></div>
        </div>
        <div class="rotator"></div>
    </div>
</div>