Loading = {
    show:function () {
        $.blockUI({
            message: '<img src="/static/image/loading.gif">',
            css:{
                zIndex:         '10011',
                padding:        '10px',
                left:           '50%',
                width:          '80px',
                marginLeft:     '-40px'

            }
        });
    },
    hide:function () {
        //查询太快看不清，做一个延时
         setTimeout(function () {
            $.unblockUI();
         },150)

    }

}