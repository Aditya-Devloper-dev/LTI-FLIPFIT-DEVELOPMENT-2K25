import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AdminService } from './admin.service';
import { GymCenter } from '../../models/gym-center/gym-center.model';

describe('AdminService', () => {
  let service: AdminService;
  let httpMock: HttpTestingController;
  const baseUrl = 'http://localhost:8080/admin';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AdminService]
    });
    service = TestBed.inject(AdminService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all centers', () => {
    const mockCenters: GymCenter[] = [
      { centerId: 1, centerName: 'Center 1', city: 'City 1', contactNumber: '123' },
      { centerId: 2, centerName: 'Center 2', city: 'City 2', contactNumber: '456' }
    ];

    service.getAllCenters().subscribe(centers => {
      expect(centers.length).toBe(2);
      expect(centers).toEqual(mockCenters);
    });

    const req = httpMock.expectOne(`${baseUrl}/centers`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCenters);
  });

  it('should fetch pending centers', () => {
    const mockPendingCenters: GymCenter[] = [
      { centerId: 3, centerName: 'Pending Center', city: 'City 3', contactNumber: '789' }
    ];

    service.getPendingCenters().subscribe(centers => {
      expect(centers.length).toBe(1);
      expect(centers).toEqual(mockPendingCenters);
    });

    const req = httpMock.expectOne(`${baseUrl}/pending-centers`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPendingCenters);
  });

  it('should approve a center', () => {
    const centerId = 1;
    const responseMessage = 'Center approved';

    service.approveCenter(centerId).subscribe(response => {
      expect(response).toBe(responseMessage);
    });

    const req = httpMock.expectOne(`${baseUrl}/approve-center/${centerId}`);
    expect(req.request.method).toBe('PUT');
    req.flush(responseMessage);
  });

  it('should fetch center by ID', () => {
    const mockCenter: GymCenter = { centerId: 1, centerName: 'Center 1', city: 'City 1', contactNumber: '123' };

    service.getCenterById(1).subscribe(center => {
      expect(center).toEqual(mockCenter);
    });

    const req = httpMock.expectOne(`${baseUrl}/center/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCenter);
  });

  it('should delete a center', () => {
    const centerId = 1;
    const responseMessage = 'Center deleted';

    service.deleteCenter(centerId).subscribe(response => {
      expect(response).toBe(responseMessage);
    });

    const req = httpMock.expectOne(`${baseUrl}/delete-center/${centerId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush(responseMessage);
  });

  it('should approve an owner', () => {
    const ownerId = 1;
    const responseMessage = 'Owner approved';

    service.approveOwner(ownerId).subscribe(response => {
      expect(response).toBe(responseMessage);
    });

    const req = httpMock.expectOne(`${baseUrl}/approve-owner/${ownerId}`);
    expect(req.request.method).toBe('PUT');
    req.flush(responseMessage);
  });
});
