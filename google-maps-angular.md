### Google Maps Integration

https://developers.google.com/maps/documentation/javascript/using-typescript
https://github.com/angular/components/blob/main/src/google-maps/README.md
https://github.com/AlperMulayim/LeaseSoftPro/commit/7e471ff4ea54d83811eed677331f9707fbd04957?diff=split

### Install 
`````
npm install @types/googlemaps  
`````

### Add google maps script to index.hhtml 
`````html
<!doctype html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <title>LeasesoftUi</title>
  <base href="/">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="icon" type="image/x-icon" href="favicon.ico">
  <script src="https://maps.googleapis.com/maps/api/js?key= YOUR_KEY" defer></script>
</head>

<body>
  <app-root></app-root>
</body>
</html>
`````


### Add compiler option type to tssconfig.app.json
`````
 "compilerOptions": {
    "outDir": "./out-tsc/app",
    "types": ["googlemaps"]
  },
`````

### map.html
``````html
<div class="map" id="map"></div>
``````
### map.ts
`````typescript
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-map-leasesoft',
  templateUrl: './map-leasesoft.component.html',
  styleUrls: ['./map-leasesoft.component.scss']
})
export class MapLeasesoftComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    const myLatLng = { lat: -25.363, lng: 131.044 };
    const myLatLng2 = { lat: -25.563, lng: 131.044 };


    const map = new google.maps.Map(
      document.getElementById("map") as HTMLElement,
      {
        zoom: 4,
        center: myLatLng,
      }
    );

   new google.maps.Marker({
      position: myLatLng,
      map,
      title: "Hello World!",
    });

      // use a Material Icon as font
  new google.maps.Marker({
    position: myLatLng2,
    map,
    label: {
      text: "A", // codepoint from https://fonts.google.com/icons
      fontFamily: "Material Icons",
      color: "#ffffff",
      fontSize: "16px",
    },
    title: "Material Icon Font Marker",
  });

  }
}
``````
### map.scss
``````scss
.map{
    height: 600px;
    width: 100%;
}
``````

