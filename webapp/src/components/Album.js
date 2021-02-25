function Album(props) {
    return (
        <div>
            <span>ISRC: {props.isrc}</span>
            <span className="ml-3">Title: {props.title}</span>
            <span className="ml-3">Release Year: {props.releaseYear}</span>
            <span className="ml-3">Artist: {props.artist}</span>
            <span className="ml-3">Content Description: {props.contentDesc}</span>
        </div>
    );
}

export default Album;
