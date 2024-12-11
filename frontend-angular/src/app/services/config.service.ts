import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private configUrl = 'http://localhost:8080/api/config';

  constructor(private http: HttpClient) {}

  saveConfig(config: any): Observable<any> {
    return this.http.post(this.configUrl, config);
  }
}