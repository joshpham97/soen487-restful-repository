import { Accordion, AccordionSummary, AccordionDetails } from '@material-ui/core';

function Album(props) {
    return (
        <Accordion>
            <AccordionSummary>
                <div>
                    <b>{props.title}</b>
                    <i> by {props.firstname} {props.lastname}</i>
                </div>
            </AccordionSummary>
            <AccordionDetails>
                <div>
                    <div>ISRC: {props.isrc}</div>
                    <div>Released: {props.releaseYear}</div>
                    <div>Description: {props.contentDesc}</div>
                </div>
            </AccordionDetails>
        </Accordion>
    );
}

export default Album;
