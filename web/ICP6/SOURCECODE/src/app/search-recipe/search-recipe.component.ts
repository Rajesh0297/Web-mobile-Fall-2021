import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})
export class SearchRecipeComponent implements OnInit {
  @ViewChild('recipe') recipes: ElementRef;
  @ViewChild('place') places: ElementRef;
  recipeValue: any;
  placeValue: any;
  venueList = [];
  recipeList = [];

  currentLat: any;
  currentLong: any;
  geolocationPosition: any;
  recipesAPIEndpoint = 'https://api.edamam.com/api/recipes/v2?type=public&q=';
  recipe_app_id = '52f66375';
  recipe_app_key = '8a7184f83b1acb3bde63307aae9106b2';
  venueAPIEndpoint = 'https://api.foursquare.com/v2/venues/search?'
  foursquareClientId = 'LON4FDHWOCVOWRE30BIOSAG2AFPJ43UED20ICV02P3BIA4UR';
  foursquareClientsecret = 'I1H1XUQ5UMUQJAR4LOTHXS3SCXTZMFJG0ONSXCVVEEUSMRZY';
  foursquareAPIVersion = '&v=20180323'
  noreceipes=false
novenues=false
  constructor(private _http: HttpClient) {
  }

  ngOnInit() {

    window.navigator.geolocation.getCurrentPosition(
      position => {
        this.geolocationPosition = position;
        this.currentLat = position.coords.latitude;
        this.currentLong = position.coords.longitude;
      });
  }

  getVenues() {

    this.recipeValue = this.recipes.nativeElement.value;
    this.placeValue = this.places.nativeElement.value;

    if (this.recipeValue !== null) {
      /**
       * Write code to get recipe
       */
      this.recipeList=[]
      this.noreceipes=false
      this._http.get(this.recipesAPIEndpoint + this.recipeValue + '&app_id=' + this.recipe_app_id + '&app_key=' + this.recipe_app_key).subscribe(data => {
        for (var i in data['hits'])
        this.recipeList.push(data['hits'][i]);
        if(!(this.recipeList.length>0)){
          this.noreceipes=true
        }
      })
     
      

    }

    if (this.placeValue != null && this.placeValue !== '' && this.recipeValue != null && this.recipeValue !== '') {
      /**
       * Write code to get place
       */
      this.venueList=[]
      this.novenues=false
       this._http.get(this.venueAPIEndpoint+ '&client_id=' + this.foursquareClientId + '&client_secret=' + this.foursquareClientsecret+ this.foursquareAPIVersion+'&near=' + this.placeValue ).subscribe(data => {
        for (var i in data['response']['venues'])
        this.venueList.push(data['response']['venues'][i]);
        if(!(this.venueList.length>0)){
          this.novenues=true
        }
      })
    }
  }
}
