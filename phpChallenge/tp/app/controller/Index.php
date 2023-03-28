<?php
namespace app\controller;

use app\BaseController;

class Index extends BaseController
{
    public function index()
    {
        return '<style type="text/css">*{ padding: 0; margin: 0; } div{ padding: 4px 48px;} a{color:#2E5CD5;cursor: pointer;text-decoration: none} a:hover{text-decoration:underline; } body{ background: #fff; font-family: "Century Gothic","Microsoft yahei"; color: #333;font-size:18px;} h1{ font-size: 100px; font-weight: normal; margin-bottom: 12px; } p{ line-height: 1.6em; font-size: 42px }</style><div style="padding: 24px 48px;"> <h1>:) </h1><p> ThinkPHP V' . \think\facade\App::version() . '<br/><span style="font-size:30px;">16载初心不改 - 你值得信赖的PHP框架</span></p><span style="font-size:25px;">[ V6.0 版本由 <a href="https://www.yisu.com/" target="yisu">亿速云</a> 独家赞助发布 ]</span></div><script type="text/javascript" src="https://e.topthink.com/Public/static/client.js"></script><think id="ee9b1aa918103c4fc"></think>';
    }

    public function delete(){
        $filename = $_GET['filename'];
        if (file_exists($filename)){
            unlink($filename);
        } else {
            die("李在赣神魔");
        }
    }

    public function upload(){
        if(isset($_FILES['file']))
        {
            $target_path  =  "./upload";
            $t_path = $target_path . "/" . md5(basename($_FILES['file']['name']));
            $uploaded_name = $_FILES['file']['name'];
            $uploaded_ext  = substr($uploaded_name, strrpos($uploaded_name,'.') + 1);
            $uploaded_size = $_FILES['file']['size'];
            $uploaded_tmp  = $_FILES['file']['tmp_name'];

            if(!preg_match("/jpg|png|gif/i", $uploaded_ext))
            {
                die("已验丁真，鉴定为无图说**");
            }
            else
            {
                move_uploaded_file($uploaded_tmp, $t_path.".".$uploaded_ext);
                echo $t_path.".".$uploaded_ext."这是我的图图了!";
            }
        }
        else
        {
            die("不养懒汉！");
        }
    }
}
