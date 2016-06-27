<?php
ini_set('memory_limit', '-1');
error_reporting(0);
if ($_SERVER['REQUEST_METHOD'] == 'POST'){
	if(!$_POST['final']){
		$fileext = substr(strrchr($_FILES['file']['name'], '.'), 1, 10);
		$tfilename = time().rand(100, 999);
		$new_name = './images/'.$tfilename.'.'.$fileext;
		$tmp_name = $_FILES['file']['tmp_name'];
		if((function_exists('move_uploaded_file') && @move_uploaded_file($tmp_name, $new_name))) {
			$data = getimagesize($new_name);
        	$src_width = $data[0];
        	$src_height = $data[1];
			if($src_width < 500 || $src_height < 370){
				$thumbPath = './images/'.$tfilename.'.'.$fileext.'.'.$fileext;
				make_thumb($new_name, $thumbPath, 500, 370, 90);
				echo json_encode(array('imgurl'=>'/images/'.$tfilename.'.'.$fileext.'.'.$fileext));
			}else{
				echo json_encode(array('imgurl'=>'/images/'.$tfilename.'.'.$fileext));
			}
		}
	}else{
		$targ_w = $targ_h = 150;
		$jpeg_quality = 90;
		
		$src = $_POST['filePath'];
		$img_r = imagecreatefromjpeg($src);
		$dst_r = ImageCreateTrueColor( $targ_w, $targ_h );
	
		imagecopyresampled($dst_r,$img_r,0,0,$_POST['x'],$_POST['y'],
		$targ_w,$targ_h,$_POST['w'],$_POST['h']);
		
		imagejpeg($dst_r,null,$jpeg_quality);
	}
	exit;
}

function make_thumb($src, $dst, $thumb_width, $thumb_height = 0, $quality = 85)
{
    if (function_exists('imagejpeg'))
    {
        $func_imagecreate = function_exists('imagecreatetruecolor') ? 'imagecreatetruecolor' : 'imagecreate';
        $func_imagecopy = function_exists('imagecopyresampled') ? 'imagecopyresampled' : 'imagecopyresized';
        $data = getimagesize($src);
        $src_width = $data[0];
        $src_height = $data[1];
        if($thumb_width > $src_width || $thumb_height > $src_height){
        	$dst_w = $src_width;
        	$dst_h = $src_height;
        	if($thumb_width < $dst_w){
        		$dst_w = $thumb_width;
	            $dst_h = ($dst_w * $src_height) / $src_width;
        	}
        	if($thumb_height < $dst_h){
        		$dst_h = $thumb_height;
        		$dst_w = ($dst_h * $src_width) / $src_height;
        	}
        	$dst_x = ($thumb_width - $dst_w) / 2;
	        $dst_y = ($thumb_height - $dst_h) / 2;
        }else{
	        if ($thumb_height == 0)
	        {
	            if ($src_width > $src_height)
	            {
	                $thumb_height = $src_height * $thumb_width / $src_width;
	            }
	            else
	            {
	                $thumb_height = $thumb_width;
	                $thumb_width = $src_width * $thumb_height / $src_height;
	            }
	            $dst_x = 0;
	            $dst_y = 0;
	            $dst_w = $thumb_width;
	            $dst_h = $thumb_height;
	        }
	        else
	        {
	            if ($src_width / $src_height > $thumb_width / $thumb_height)
	            {
	                $dst_w = $thumb_width;
	                $dst_h = ($dst_w * $src_height) / $src_width;
	                $dst_x = 0;
	                $dst_y = ($thumb_height - $dst_h) / 2;
	            }
	            else
	            {
	                $dst_h = $thumb_height;
	                $dst_w = ($src_width * $dst_h) / $src_height;
	                $dst_y = 0;
	                $dst_x = ($thumb_width - $dst_w) / 2;
	            }
	        }
        }
        switch ($data[2])
        {
            case 1:
                $im = imagecreatefromgif($src);
                break;
            case 2:
                $im = imagecreatefromjpeg($src);
                break;
            case 3:
                $im = imagecreatefrompng($src);
                break;
            default:
                trigger_error("Cannot process this picture format: " .$data['mime']);
                break;
        }
        $ni = $func_imagecreate($thumb_width, $thumb_height);
        if ($func_imagecreate == 'imagecreatetruecolor')
        {
            imagefill($ni, 0, 0, imagecolorallocate($ni, 255, 255, 255));
        }
        else
        {
            imagecolorallocate($ni, 255, 255, 255);
        }
        $func_imagecopy($ni, $im, $dst_x, $dst_y, 0, 0, $dst_w, $dst_h, $src_width, $src_height);
        imagejpeg($ni, $dst, $quality);
        return is_file($dst) ? $dst : false;
    }
    else
    {
        trigger_error("Unable to process picture.", E_USER_ERROR);
    }
}
