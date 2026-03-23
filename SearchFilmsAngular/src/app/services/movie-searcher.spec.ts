import { TestBed } from '@angular/core/testing';

import { MovieSearcher } from './movie-searcher';

describe('MovieSearcher', () => {
  let service: MovieSearcher;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MovieSearcher);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
