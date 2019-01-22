export default class ApiService {

    _apiBase = 'http://0.0.0.0:9000/hello';

    getResource = async (options) => {
        const res = (options === null) ? await fetch(`${this._apiBase}`) : await fetch(`${this._apiBase}`, options);

        if (!res.ok) {
            throw new Error(`Could not fetch ${this._apiBase}, received ${res.status}`)
        }
        return await res.json();
    };

    getHello = async () => {
        const res = await this.getResource();
        return this._transformHelloResponse(res)
    };

    postHello = async (personName) => {
        const options = {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: personName
            })
        };
        const res = await this.getResource(options);
        return this._transformHelloResponse(res)
    };

    _transformHelloResponse = (response) => {
        return {
            message: response.message
        }
    };
}