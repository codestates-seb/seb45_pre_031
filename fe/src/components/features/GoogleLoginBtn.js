import { GoogleLogin } from '@react-oauth/google';
import { GoogleOAuthProvider } from '@react-oauth/google';

function GoogleLoginBtn () {
    const clientId = process.env.REACT_APP_CLIENT_ID;

    return (
        <>
            <GoogleOAuthProvider clientId={clientId}>
                <GoogleLogin
                    onSuccess={(res) => {
                        console.log(res);
                    }}
                    onError={(err) => {
                        console.log(err);
                    }}
                />
            </GoogleOAuthProvider>
        </>
    )
}

export default GoogleLoginBtn;