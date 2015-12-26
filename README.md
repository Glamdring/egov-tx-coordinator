Transaction coordinator for government services communication
-------------------------------------------------------------

This is a thin transaction coordinator for government e-services. The flow is as follows:

- the requesting system creates a transaction by calling the `/transaction/create` endpoint. The coordinator responds with a transaction id
- the requesting system sends a request to the responding system with exactly the same parameters as used in the transaction creation call, plus the obtained transaction id.
- the responding system verifies the transaction by passing the transaction id and the request parameters to `/transaction/verify`.

Transaction creation requests must be digitally signed in order to authenticate the requester.
The transaction coordinator verifies (through calls to external services) whether the requester is allowed to access the requested data or endpoint. That way the responding system does not need to do these checks itself - it only has to verify the transaction.

Security risks and how to address them in terms of infrastructure:
- MITM attacks - communication between requester, responder and coordinator must use TLS with a valid coordinator certificate
- stolen private keys - private keys of requesters should be stored on HSMs

