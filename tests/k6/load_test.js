import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
    stages: [{duration: '10s', target: 10}, {duration: '30s', target: 50}, {duration: '10s', target: 0},],

    thresholds: {
        http_req_duration: ['p(95)<2000'],
    },
};

export default function () {

    const resList = http.get('http://localhost:8080/classes');

    check(resList, {
        'Ders listesi açıldı (Status 200)': (r) => r.status === 200,
    });

    sleep(1);

    const payload = JSON.stringify({
        memberId: 1, classId: 1,
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const resBook = http.post('http://localhost:8080/reservations', payload, params);

    check(resBook, {
        'Rezervasyon isteğine cevap döndü': (r) => r.status === 200 || r.status === 500,
    });

    sleep(1);
}