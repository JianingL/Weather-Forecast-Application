<?php
header("Access-Control-Allow-Origin: *");
  if(isset($_GET["address"]))
  {
      $completeurl ="https://maps.google.com/maps/api/geocode/xml?address=". $_GET["address"].','.$_GET["city"].','.$_GET["state"]."&key=AIzaSyDUJ73Y21wXJ6twEoM-1g6lWbKQbw8EJqo";
      $xml = simplexml_load_file($completeurl);
      if($xml->status=="OK"){
      $lat = $xml->result->geometry->location->lat;
      $lng = $xml->result->geometry->location->lng;
      if($_GET["degree"]=="fahrenheit"){
          $unitsvalue="us";
      }else if($_GET["degree"]=="celsius"){
          $unitsvalue="si";
      }
      $url2 = "https://api.forecast.io/forecast/c858f7b2369496003a21076ec639af77/".$lat.','.$lng.'?units='.$unitsvalue.'&exclude=flags';
      $json = file_get_contents($url2);
      $obj = json_decode($json);
      $precipIntensity = $obj->currently->precipIntensity;
      $data = $obj->daily->data;
      date_default_timezone_set($obj->timezone);
      $sunriseTime = $data[0]->sunriseTime;
      $sunsetTime = $data[0]->sunsetTime;
      $sunrise = date("h:i A", $sunriseTime);
      $sunset = date("h:i A", $sunsetTime);
    }
  }
echo $json;
?>
<?php 
    if(isset($_GET["address"]) && ($xml->status!="OK")): 
?>
    <script>
        alert("Invalid Address!");
    </script>
<?php endif; ?> 