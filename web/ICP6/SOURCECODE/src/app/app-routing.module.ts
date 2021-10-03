import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContactusComponent } from './contactus/contactus.component';
import {HomeComponent} from './home/home.component';
import {SearchRecipeComponent} from './search-recipe/search-recipe.component';

const appRoutes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent},
  { path: 'search-recipe', component: SearchRecipeComponent},
  { path: 'shopping-list', component: ContactusComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
