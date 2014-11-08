<?php
  header('Content-Type: text/plain');
  if (isset($_COOKIE['custom_settings'])) {
    // We should verify to make sure this thing is legit.
    $custom_settings = urldecode($_COOKIE['custom_settings']);
    echo "COOKIE: ".$_COOKIE['custom_settings']."\n";
    echo "COOKIE (decoded): ".$custom_settings."\n";
    $hash = sha1(AUTH_SECRET . $custom_settings);
    echo "hash data: ".constant("AUTH_SECRET") .$custom_settings."\n";
    echo "hash: ".$hash ."\n";
    echo "received hash: ".$_COOKIE['custom_settings_hash']."\n";
    if (isset($_GET['unset'])) {
	setcookie('custom_settings', null, -1, "/");
	setcookie('custom_settings_hash', null, -1, "/");
	die("Cookie unset");
    }
    if ($hash !== $_COOKIE['custom_settings_hash']) {
      die("Why would you hack Section Chief Steve's site? :(");
    }
    // we only support one setting for now, but we might as well put this in.
    $settings_array = explode("\n", $custom_settings);
    print_r($settings_array);
    $custom_settings = array();
    for ($i = 0; $i < count($settings_array); $i++) {
      $setting = $settings_array[$i];
      $setting = unserialize($setting);
      $custom_settings[] = $setting;
    }
  } else {
    $custom_settings = array(0 => true);
    $cs_data = urlencode(serialize(true));
    $cs_hash = sha1(AUTH_SECRET . serialize(true));
    setcookie('custom_settings', $cs_data, time() + 86400 * 30, "/");
    setcookie('custom_settings_hash', $cs_hash, time() + 86400 * 30, "/");
    echo "COOKIE SET:\n";
    echo "data: ".$cs_data."\n";
    echo "data (decoded): ".urldecode($cs_data)."\n"; echo "hash: ".$cs_hash."\n";
  }
?>
