(function () {
    CKEDITOR.dialog.add("multiimg",
        function (a) {
            return {
                title: "批量上传图片",
                minWidth: "660px",
                minHeight: "400px",
                contents: [{
                    id: "tab1",
                    label: "",
                    title: "",
                    expand: true,
                    width: "420px",
                    height: "300px",
                    padding: 0,
                    elements: [{
                        type: "html",
                        style: "width:660px;height:400px",
                        html: '<iframe id="uploadFrame" src="/image/image.html?v=' + new Date().getSeconds() + '" frameborder="0"></iframe>'
                    }]
                }],
                onOk: function () {
                    // 拿到對象,這個在iframe里定義
                    var num = window.fff;
                    //点击确定按钮后的操作
                    a.insertHtml("编辑器追加内容" + num);
                },
                onShow: function () {
                    document.getElementById("uploadFrame").setAttribute("src", "/image/image.html?v=' +new Date().getSeconds() + '");
                }
            }
        })
})();