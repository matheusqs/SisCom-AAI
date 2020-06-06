import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-page-template',
  templateUrl: './page-template.component.html',
  styleUrls: ['./page-template.component.scss']
})
export class PageTemplateComponent implements OnInit {

  @Input()
  title: string;

  @Input()
  goBack: () => void;

  constructor() { }

  ngOnInit() {
  }

}
